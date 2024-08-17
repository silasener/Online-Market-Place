package tr.com.service;

import tr.com.dto.ProductDto;
import tr.com.dto.SellerDto;
import tr.com.request.ProductFilterRequest;

import java.util.List;
import java.util.Map;

public interface ProductService {

    List<ProductDto> getAvailableProductsForUser(String userId);//sil

    Map<String, Object> getAvailableProductsForUser(String userId, int page, int size);

    List<ProductDto> getAllProducts();

    Map<String, Object> getAllProducts(int page, int size);

    ProductDto createNewProduct(ProductDto productDto);

    ProductDto deleteProductById(String productId);

    ProductDto updateExistingProduct(String productId, ProductDto updateProductDto);

    List<SellerDto> getSellersByProductId(String productId);

    ProductDto getProductById(String productId);

    List<ProductDto> getProductsBySellerId(String sellerId);

   List<ProductDto> filterAvailableProductsForUser(ProductFilterRequest productFilterRequest);//sil

    Map<String, Object> filterAvailableProductsForUser(ProductFilterRequest productFilterRequest, int page, int size);

    List<ProductDto>  filterProducts(ProductFilterRequest productFilterRequest);
}
