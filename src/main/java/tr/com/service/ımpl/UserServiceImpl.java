package tr.com.obss.jip.finalproject.service.ımpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.obss.jip.finalproject.common.Constants;
import tr.com.obss.jip.finalproject.dto.ProductDto;
import tr.com.obss.jip.finalproject.dto.SellerDto;
import tr.com.obss.jip.finalproject.dto.UserDto;
import tr.com.obss.jip.finalproject.exception.*;
import tr.com.obss.jip.finalproject.mapper.ProductMapper;
import tr.com.obss.jip.finalproject.mapper.SellerMapper;
import tr.com.obss.jip.finalproject.mapper.UserMapper;
import tr.com.obss.jip.finalproject.model.Product;
import tr.com.obss.jip.finalproject.model.Role;
import tr.com.obss.jip.finalproject.model.Seller;
import tr.com.obss.jip.finalproject.model.User;
import tr.com.obss.jip.finalproject.repository.ProductRepository;
import tr.com.obss.jip.finalproject.repository.SellerRepository;
import tr.com.obss.jip.finalproject.repository.UserRepository;
import tr.com.obss.jip.finalproject.request.ProductFilterRequest;
import tr.com.obss.jip.finalproject.service.RoleService;
import tr.com.obss.jip.finalproject.service.UserService;
import tr.com.obss.jip.finalproject.utils.StringUtils;

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
        List<UserDto> userDtos = new ArrayList<>();
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
            userDtos.add(userMapper.toDto(userRepository.save(userModel)));
        }
        return userDtos;
    }

    @Transactional
    @Override
    public UserDto createNewUser(UserDto userDto) {
        Objects.requireNonNull(userDto, "userDto cannot be null");
        Objects.requireNonNull(userDto.getName(), "userName cannot be null");
        Objects.requireNonNull(userDto.getSurname(), "surname cannot be null");
        Objects.requireNonNull(userDto.getEmail(), "email cannot be null");
        Objects.requireNonNull(userDto.getPassword(), "password cannot be null");

        Optional<User> existingUsername=userRepository.findUserByUsername(userDto.getUsername());

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
    public SellerDto createNewSeller(SellerDto sellerDto) {
        Objects.requireNonNull(sellerDto, "sellerDto cannot be null");
        Objects.requireNonNull(sellerDto.getName(), "sellerName cannot be null");
        Objects.requireNonNull(sellerDto.getSurname(), "surname cannot be null");
        Objects.requireNonNull(sellerDto.getEmail(), "email cannot be null");

        Optional<Seller> existingSeller = sellerRepository.findSellerByVenderCode(sellerDto.getVenderCode());
        if (existingSeller.isPresent()) {
            throw new SellerVenderCodeAlreadyInUseException(sellerDto.getVenderCode());
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

        List<UserDto> userDtos = users.stream()
                .filter(user -> user.getRoles().stream().noneMatch(role -> "ROLE_ADMIN".equals(role.getName())))
                .map(userMapper::toDto)
                .collect(Collectors.toList());

        return userDtos;
    }


    @Override
    public UserDto deleteUserById(String userId) {
        Objects.requireNonNull(userId, "userId cannot be null");
        final User user = userRepository.findUserById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(user);
        return userMapper.toDto(user);
    }


    @Override
    public UserDto updateExistingUser(String userId, UserDto updateUserDto) {
        Objects.requireNonNull(userId, "userId cannot be null");
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
        List<SellerDto> sellerDtoList = sellerRepository.findAll().stream().map(sellerMapper::toDto).collect(Collectors.toList());
        return sellerDtoList;
    }


    @Transactional
    @Override
    public SellerDto deleteSellerById(String sellerId) {
        final Seller seller = sellerRepository.findSellerById(UUID.fromString(sellerId)).orElseThrow(() -> new SellerNotFoundException(sellerId));

        for (Product product : seller.getProducts()) {
            product.getSellers().remove(seller);
            productRepository.save(product);
        }

        seller.getProducts().clear();
        sellerRepository.save(seller);

        final Seller updatedSeller = sellerRepository.findSellerById(UUID.fromString(sellerId)).orElseThrow(() -> new SellerNotFoundException(sellerId));

        sellerRepository.deleteSellerById(updatedSeller.getId());
        sellerRepository.save(updatedSeller);

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

        List<SellerDto> blockedSeller = user.getBlackListedSellers().stream().map(sellerMapper::toDto).collect(Collectors.toList());

        return blockedSeller;
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
        List<ProductDto> userFavProductDtoList = user.getFavoriteProducts().stream().map(productMapper::toDto).collect(Collectors.toList());
        return userFavProductDtoList;
    }

    @Override
    public List<UserDto> findUsersByIds(List<String> userIds) {
        List<User> allUsers = userRepository.findAll();

        List<User> matchingUsers = allUsers.stream().filter(user -> userIds.contains(String.valueOf(user.getId()))).collect(Collectors.toList());

        if (matchingUsers.isEmpty()) {
            throw new UserNotFoundException();
        }

        List<UserDto> filteredUsers = matchingUsers.stream().map(userMapper::toDto).collect(Collectors.toList());

        return filteredUsers;
    }

    @Override
    public List<SellerDto> findSellersByIds(List<String> sellerIds) {
        List<Seller> allSellers = sellerRepository.findAll();

        List<Seller> matchingSellers = allSellers.stream().filter(seller -> sellerIds.contains(String.valueOf(seller.getId()))).collect(Collectors.toList());

        if (matchingSellers.isEmpty()) {
            throw new SellerNotFoundException();
        }

        List<SellerDto> filteredSellers = matchingSellers.stream().map(sellerMapper::toDto).collect(Collectors.toList());

        return filteredSellers;
    }

    @Override
    public List<ProductDto> filterFavoriteProducts(ProductFilterRequest productFilterRequest) {
        List<ProductDto> favoriteProductsDto = findFavoriteProductsByUser(productFilterRequest.getUserId());
        List<Product> favoriteProducts = favoriteProductsDto.stream().map(productMapper::toModel).collect(Collectors.toList());


        List<Product> filteredFavoriteProducts=favoriteProducts.stream()
                .filter(product ->   ((productFilterRequest.getProductNames().isEmpty() || productFilterRequest.getProductNames().contains(product.getName())) &&
                        (productFilterRequest.getCategories().isEmpty() || productFilterRequest.getCategories().contains(product.getCategory())) &&
                        (productFilterRequest.getBrands().isEmpty() || productFilterRequest.getBrands().contains(product.getBrand())))).collect(Collectors.toList());

        if (filteredFavoriteProducts.isEmpty()) {
            throw new ProductNotFoundForFilterException();
        }

        List<ProductDto> filteredFavoriteProductsDto = filteredFavoriteProducts.stream().map(productMapper::toDto).collect(Collectors.toList());


        return filteredFavoriteProductsDto;
    }
}
