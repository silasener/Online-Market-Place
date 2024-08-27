package tr.com.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.common.Constants;
import tr.com.dto.ProductDto;
import tr.com.dto.SellerDto;
import tr.com.dto.UserDto;
import tr.com.exception.*;
import tr.com.mapper.ProductMapper;
import tr.com.mapper.SellerMapper;
import tr.com.mapper.UserMapper;
import tr.com.model.Product;
import tr.com.model.Role;
import tr.com.model.Seller;
import tr.com.model.User;
import tr.com.repository.ProductRepository;
import tr.com.repository.SellerRepository;
import tr.com.repository.UserRepository;
import tr.com.request.CreateNewSellerRequest;
import tr.com.request.CreateNewUserRequest;
import tr.com.request.ProductFilterRequest;
import tr.com.request.UpdateExistingUserRequest;
import tr.com.service.RoleService;
import tr.com.service.UserService;
import tr.com.utils.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final SellerMapper sellerMapper;

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto findUserById(String userId) {
        Objects.requireNonNull(userId, "userId cannot be null");
        return userMapper.toDto(userRepository.findUserById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException(userId)));
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        Objects.requireNonNull(username, "username cannot be null");
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void checkAndCreateAdminUser() {
        userRepository.findUserByUsername(Constants.ADMIN_USER).orElseGet(() -> {
            final Role adminRole = roleService.findByName(Constants.Roles.ADMIN);
            User user = new User();
            user.setEnabled(Boolean.TRUE);
            user.setEmail("admin@admin.com");
            user.setName("admin");
            user.setSurname("admin");
            user.setUsername(Constants.ADMIN_USER);
            user.setPassword(passwordEncoder.encode("pass"));
            user.addRole(adminRole);
            userRepository.save(user);
            return user;
        });

    }

    @Transactional
    @Override
    public List<UserDto> generateSampleUsers(int userSize) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (int i = 0; i < userSize; i++) {
            final UserDto userDto = UserDto.builder()
                    .name(StringUtils.generateRandomString(10))
                    .surname(StringUtils.generateRandomString(10))
                    .email(StringUtils.generateRandomString(5) + "@gmail.com")
                    .build();
            userDto.setUsername(generateUsername(userDto));
            final User userModel = userMapper.toModel(userDto);
            userModel.setPassword(passwordEncoder.encode("pass"));
            final Role userRole = roleService.findByName(Constants.Roles.USER);
            userModel.addRole(userRole);
            userDtoList.add(userMapper.toDto(userRepository.save(userModel)));
        }
        return userDtoList;
    }

    @Transactional
    @Override
    public UserDto createNewUser(CreateNewUserRequest createNewUserRequest) {
        final UserDto userDto = UserDto.builder().id(UUID.randomUUID().toString())
                .name(createNewUserRequest.getName())
                .surname(createNewUserRequest.getSurname())
                .username(createNewUserRequest.getUsername())
                .email(createNewUserRequest.getEmail())
                .password(createNewUserRequest.getPassword())
                .build();

        Optional<User> existingUsername = userRepository.findUserByUsername(userDto.getUsername());

        if (existingUsername.isPresent()) {
            throw new UsernameAlreadyInUseException(userDto.getUsername());
        }

        if (StringUtils.isBlank(userDto.getUsername())) {
            userDto.setUsername(generateUsername(userDto));
        }
        final User user = userMapper.toModel(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        final Role userRole = roleService.findByName(Constants.Roles.USER);
        user.addRole(userRole);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public SellerDto createNewSeller(CreateNewSellerRequest createNewSellerRequest) {
        final SellerDto sellerDto = SellerDto.builder().id(UUID.randomUUID().toString())
                .name(createNewSellerRequest.getName())
                .surname(createNewSellerRequest.getSurname())
                .email(createNewSellerRequest.getEmail())
                .vendorCode(createNewSellerRequest.getVendorCode())
                .build();

        Optional<Seller> existingSeller = sellerRepository.findSellerByVendorCode(sellerDto.getVendorCode());
        if (existingSeller.isPresent()) {
            throw new SellerVendorCodeAlreadyInUseException(sellerDto.getVendorCode());
        }

        final Seller seller = sellerMapper.toModel(sellerDto);
        return sellerMapper.toDto(sellerRepository.save(seller));
    }


    @Override
    public String generateUsername(UserDto userDto) {
        Objects.requireNonNull(userDto, "userDto cannot be null");
        Objects.requireNonNull(userDto.getName(), "userName cannot be null");
        Objects.requireNonNull(userDto.getSurname(), "surname cannot be null");
        Objects.requireNonNull(userDto.getEmail(), "email cannot be null");
        return String.format("%s.%s", userDto.getName().replaceAll("\\s+", "").toLowerCase(), userDto.getSurname().replaceAll("\\s+", "").toLowerCase());
    }


    @Override
    public void blockSellerForUser(String userId, String sellerId) {
        User user = userRepository.findUserById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException(userId));
        Seller seller = sellerRepository.findSellerById(UUID.fromString(sellerId)).orElseThrow(() -> new SellerNotFoundException(sellerId));

        user.addBlackListedSeller(seller);
        userRepository.save(user);
    }

    @Override
    public void unblockSeller(String userId, String sellerId) {
        final User user = userRepository.findUserById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException(userId));
        final Seller seller = sellerRepository.findSellerById(UUID.fromString(sellerId)).orElseThrow(() -> new SellerNotFoundException(sellerId));

        user.removeBlackListedSeller(seller);
        userRepository.save(user);
    }


    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .filter(user -> user.getRoles().stream().noneMatch(role -> "ROLE_ADMIN".equals(role.getName())))
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public UserDto deleteUserById(String userId) {
        Objects.requireNonNull(userId, "userId cannot be null");
        final User user = userRepository.findUserById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(user);
        return userMapper.toDto(user);
    }


    @Override
    public UserDto updateExistingUser(String userId, UpdateExistingUserRequest updateExistingUserRequest) {
        UserDto updateUserDto = UserDto.builder().email(updateExistingUserRequest.getEmail())
                .name(updateExistingUserRequest.getName())
                .surname(updateExistingUserRequest.getSurname())
                .username(updateExistingUserRequest.getUsername())
                .build();

        final User user = userRepository.findUserById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException(userId));

        Optional<User> existingUsername = userRepository.findUserByUsernameAndIdNot(updateUserDto.getUsername(), user.getId());

        if (existingUsername.isPresent()) {
            throw new UsernameAlreadyInUseException(updateUserDto.getUsername());
        }


        if (StringUtils.isNotBlank(updateUserDto.getName())) {
            user.setName(updateUserDto.getName());
        }
        if (StringUtils.isNotBlank(updateUserDto.getSurname())) {
            user.setSurname(updateUserDto.getSurname());
        }
        if (StringUtils.isNotBlank(updateUserDto.getEmail())) {
            user.setEmail(updateUserDto.getEmail());
        }
        if (StringUtils.isNotBlank(updateUserDto.getUsername())) {
            user.setUsername(updateUserDto.getUsername());
        }

        return userMapper.toDto(userRepository.save(user));
    }


    @Override
    public List<SellerDto> findAllSellers() {
        return sellerRepository.findAll().stream().map(sellerMapper::toDto).collect(Collectors.toList());
    }


    @Transactional
    @Override
    public SellerDto deleteSellerById(String sellerId) {
        final Seller seller = sellerRepository.findSellerById(UUID.fromString(sellerId)).orElseThrow(() -> new SellerNotFoundException(sellerId));

        for (Product product : seller.getProducts()) {
            product.getSellers().remove(seller);
        }

       for( User user :seller.getBlackListedByUsers()){
           user.getBlackListedSellers().remove(seller);
       }

        sellerRepository.delete(seller);
        return sellerMapper.toDto(seller);
    }

    @Override
    public void addProductToFavoriteList(String productId, String userId) {
        final Product product = productRepository.findProductById(UUID.fromString(productId)).orElseThrow(() -> new ProductNotFoundException(productId));
        final User user = userRepository.findUserById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException(userId));

        user.addFavoriteProduct(product);
        userRepository.save(user);
    }

    @Override
    public void removeProductFromFavoriteList(String productId, String userId) {
        final Product product = productRepository.findProductById(UUID.fromString(productId)).orElseThrow(() -> new ProductNotFoundException(productId));
        final User user = userRepository.findUserById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException(userId));

        user.removeFavoriteProduct(product);
        userRepository.save(user);
    }


    @Override
    public List<SellerDto> findBlockedSellersByUserId(String userId) {
        final User user = userRepository.findUserById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException(userId));

        return user.getBlackListedSellers().stream().map(sellerMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void removeProductFromSeller(String sellerId, String productId) {
        Seller seller = sellerRepository.findSellerById(UUID.fromString(sellerId)).orElseThrow(() -> new SellerNotFoundException(sellerId));
        Product product = productRepository.findById(UUID.fromString(productId)).orElseThrow(() -> new ProductNotFoundException(productId));

        seller.getProducts().remove(product);
        product.getSellers().remove(seller);
        productRepository.save(product);
        sellerRepository.save(seller);
    }

    @Override
    public SellerDto addProductToSeller(String productId, String sellerId) {
        final Product product = productRepository.findProductById(UUID.fromString(productId)).orElseThrow(() -> new ProductNotFoundException(productId));
        final Seller seller = sellerRepository.findSellerById(UUID.fromString(sellerId)).orElseThrow(() -> new SellerNotFoundException(sellerId));

        if (product.getSellers().contains(seller)) {
            throw new SellerAlreadyAssociatedWithProductException(String.valueOf(seller.getId()));
        }

        product.getSellers().add(seller);
        productRepository.save(product);
        return sellerMapper.toDto(seller);
    }

    @Override
    public List<ProductDto> findFavoriteProductsByUser(String userId) {
        final User user = userRepository.findUserById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getFavoriteProducts().stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findUsersByIds(List<String> userIds) {
        List<User> allUsers = userRepository.findAll();

        List<User> matchingUsers = allUsers.stream().filter(user -> userIds.contains(String.valueOf(user.getId()))).toList();

        if (matchingUsers.isEmpty()) {
            throw new UserNotFoundException();
        }

        return matchingUsers.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SellerDto> findSellersByIds(List<String> sellerIds) {
        List<Seller> allSellers = sellerRepository.findAll();

        List<Seller> matchingSellers = allSellers.stream().filter(seller -> sellerIds.contains(String.valueOf(seller.getId()))).toList();

        if (matchingSellers.isEmpty()) {
            throw new SellerNotFoundException();
        }

        return matchingSellers.stream().map(sellerMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> filterFavoriteProducts(ProductFilterRequest productFilterRequest) {
        List<ProductDto> favoriteProductsDto = findFavoriteProductsByUser(productFilterRequest.getUserId());
        List<Product> favoriteProducts = favoriteProductsDto.stream().map(productMapper::toModel).toList();


        List<Product> filteredFavoriteProducts = favoriteProducts.stream()
                .filter(product -> ((productFilterRequest.getProductNames().isEmpty() || productFilterRequest.getProductNames().contains(product.getName())) &&
                        (productFilterRequest.getCategories().isEmpty() || productFilterRequest.getCategories().contains(product.getCategory())) &&
                        (productFilterRequest.getBrands().isEmpty() || productFilterRequest.getBrands().contains(product.getBrand())))).toList();

        if (filteredFavoriteProducts.isEmpty()) {
            throw new ProductNotFoundForFilterException();
        }

        return filteredFavoriteProducts.stream().map(productMapper::toDto).collect(Collectors.toList());
    }
}
