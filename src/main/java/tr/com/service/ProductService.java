package tr.com.service;

import tr.com.dto.ProductDto;
import tr.com.dto.SellerDto;
import tr.com.request.ProductFilterRequest;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAvailableProductsForUser(String userId);

    List<ProductDto> getAllProducts();

    ProductDto createNewProduct(ProductDto productDto);

    ProductDto deleteProductById(String productId);

    ProductDto updateExistingProduct(String productId, ProductDto updateProductDto);

    List<SellerDto> getSellersByProductId(String productId);

    ProductDto getProductById(String productId);

    List<ProductDto> getProductsBySellerId(String sellerId);

    List<ProductDto> filterAvailableProductsForUser(ProductFilterRequest productFilterRequest);

    List<ProductDto>  filterProducts(ProductFilterRequest productFilterRequest);
}
