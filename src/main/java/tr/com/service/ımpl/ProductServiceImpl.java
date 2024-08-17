package tr.com.service.ımpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.dto.ProductDto;
import tr.com.dto.SellerDto;
import tr.com.exception.*;
import tr.com.exception.*;
import tr.com.mapper.ProductMapper;
import tr.com.mapper.SellerMapper;
import tr.com.model.Product;
import tr.com.model.Seller;
import tr.com.model.User;
import tr.com.repository.ProductRepository;
import tr.com.repository.SellerRepository;
import tr.com.repository.UserRepository;
import tr.com.request.ProductFilterRequest;
import tr.com.service.ProductService;
import tr.com.utils.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    private final ProductMapper productMapper;
    private final SellerMapper sellerMapper;

    @Override
    public List<ProductDto> getAvailableProductsForUser(String userId) {

        final User user = userRepository.findUserById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException(userId));

        List<UUID> blockedSellerIds = user.getBlackListedSellers().stream().map(Seller::getId).collect(Collectors.toList());

        List<Seller> availableSellers = sellerRepository.findAll().stream().filter(seller -> !blockedSellerIds.contains(seller.getId())).collect(Collectors.toList());

        List<Product> newProducts = productRepository.findAll().stream().filter(product -> product.getSellers().stream().anyMatch(availableSellers::contains)).collect(Collectors.toList());

        List<ProductDto> productDtoList = newProducts.stream().map(productMapper::toDto).collect(Collectors.toList());

        return productDtoList;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductDto> allProduct = productRepository.findAll().stream().map(productMapper::toDto).collect(Collectors.toList());
        return allProduct;
    }


    @Transactional
    @Override
    public ProductDto createNewProduct(ProductDto productDto) {
        Objects.requireNonNull(productDto, "productDto cannot be null");
        Objects.requireNonNull(productDto.getName(), "productName cannot be null");
        Objects.requireNonNull(productDto.getBrand(), "brand cannot be null");
        Objects.requireNonNull(productDto.getCategory(), "category cannot be null");

        Optional<Product> existingProduct = productRepository.findProductByNameAndBrandAndCategory(productDto.getName(), productDto.getBrand(), productDto.getCategory());
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            throw new ProductAlreadyExistingException(String.valueOf(product.getId()));
        } else {
            final Product product = productMapper.toModel(productDto);
            return productMapper.toDto(productRepository.save(product));
        }
    }


    @Override
    public ProductDto deleteProductById(String productId) {
        final Product product = productRepository.findProductById(UUID.fromString(productId)).orElseThrow(() -> new ProductNotFoundException(productId));

        for (User user : product.getFavoritedByUsers()) {
            user.getFavoriteProducts().remove(product);
        }

        for (Seller seller : product.getSellers()) {
            seller.getProducts().remove(product);
        }

        productRepository.delete(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto updateExistingProduct(String productId, ProductDto updateProductDto) {
        Objects.requireNonNull(productId, "productId cannot be null");
        final Product product = productRepository.findProductById(UUID.fromString(productId)).orElseThrow(() -> new ProductNotFoundException(productId));

        Optional<Product> productExisting = productRepository.findProductByNameAndBrandAndCategoryAndIdNot(updateProductDto.getName()
                , updateProductDto.getBrand(), updateProductDto.getCategory(), product.getId());

        if (productExisting.isPresent()) {
            throw new ProductAlreadyExistingException(String.valueOf(productExisting.get().getId()));
        }

        if (StringUtils.isNotBlank(updateProductDto.getName())) {
            product.setName(updateProductDto.getName());
        }
        if (StringUtils.isNotBlank(String.valueOf(updateProductDto.getCategory()))) {
            product.setCategory(updateProductDto.getCategory());
        }
        if (StringUtils.isNotBlank(String.valueOf(updateProductDto.getBrand()))) {
            product.setBrand(updateProductDto.getBrand());
        }

        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public List<SellerDto> getSellersByProductId(String productId) {
        final Product product = productRepository.findProductById(UUID.fromString(productId)).orElseThrow(() -> new ProductNotFoundException(productId));

        final List<Seller> sellerList = sellerRepository.findAll().stream().filter(seller -> seller.getProducts().contains(product)).collect(Collectors.toList());

        List<SellerDto> sellerDtoList = sellerList.stream()
                .map(sellerMapper::toDto)
                .collect(Collectors.toList());

        return sellerDtoList;
    }

    @Override
    public ProductDto getProductById(String productId) {
        final Product product = productRepository.findProductById(UUID.fromString(productId)).orElseThrow(() -> new ProductNotFoundException(productId));
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getProductsBySellerId(String sellerId) {
        final Seller seller = sellerRepository.findSellerById(UUID.fromString(sellerId)).orElseThrow(() -> new SellerNotFoundException(sellerId));

        List<ProductDto> productDtoBySeller = seller.getProducts().stream().map(productMapper::toDto).collect(Collectors.toList());
        return productDtoBySeller;
    }

    @Override
    public List<ProductDto> filterAvailableProductsForUser(ProductFilterRequest productFilterRequest) {
        List<ProductDto> availableProductsDto = getAvailableProductsForUser(productFilterRequest.getUserId());
        List<Product> availableProducts = availableProductsDto.stream().map(productMapper::toModel).collect(Collectors.toList());

        List<Product> filteredProductsForUser = availableProducts.stream()
                .filter(product ->
                        ((productFilterRequest.getProductNames().isEmpty() || productFilterRequest.getProductNames().contains(product.getName())) &&
                                (productFilterRequest.getCategories().isEmpty() || productFilterRequest.getCategories().contains(product.getCategory())) &&
                                (productFilterRequest.getBrands().isEmpty() || productFilterRequest.getBrands().contains(product.getBrand())))).collect(Collectors.toList());

        if (filteredProductsForUser.isEmpty()) {
            throw new ProductNotFoundForFilterException();
        }

        List<ProductDto> filteredProductDto = filteredProductsForUser.stream().map(productMapper::toDto).collect(Collectors.toList());
        return filteredProductDto;
    }

    @Override
    public List<ProductDto> filterProducts(ProductFilterRequest productFilterRequest) {
        List<Product> allProducts = getAllProducts().stream().map(productMapper::toModel).collect(Collectors.toList());

        List<Product> filteredProductsForAdmin = allProducts.stream()
                .filter(product ->
                        ((productFilterRequest.getProductNames().isEmpty() || productFilterRequest.getProductNames().contains(product.getName())) &&
                                (productFilterRequest.getCategories().isEmpty() || productFilterRequest.getCategories().contains(product.getCategory())) &&
                                (productFilterRequest.getBrands().isEmpty() || productFilterRequest.getBrands().contains(product.getBrand())))).collect(Collectors.toList());

        if (filteredProductsForAdmin.isEmpty()) {
            throw new ProductNotFoundForFilterException();
        }

        List<ProductDto> filteredProductDto = filteredProductsForAdmin.stream().map(productMapper::toDto).collect(Collectors.toList());
        return filteredProductDto;
    }


}
