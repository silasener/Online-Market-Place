package tr.com.service;

import tr.com.dto.ProductDto;
import tr.com.dto.SellerDto;
import tr.com.request.CreateNewProductRequest;
import tr.com.request.ProductFilterRequest;
import tr.com.request.UpdateExistingProductRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProductService {

    Map<String, Object> getAvailableProductsForUser(String userId, int page, int size);

    Map<String, Object> getAllProducts(int page, int size);

    ProductDto createNewProduct(CreateNewProductRequest createNewProductRequest);

    ProductDto deleteProductById(String productId);

    ProductDto updateExistingProduct(String productId, UpdateExistingProductRequest updateExistingProductRequest);

    List<SellerDto> getSellersByProductId(String productId);

    ProductDto getProductById(String productId);

    List<ProductDto> getProductsBySellerId(String sellerId);

    Map<String, Object> filterAvailableProductsForUser(ProductFilterRequest productFilterRequest, int page, int size);

    Map<String, Object> filterProducts(ProductFilterRequest productFilterRequest, int page, int size);

    void checkAndLoadSqlData() throws IOException;
}
