package tr.com.obss.jip.finalproject.service;

import tr.com.obss.jip.finalproject.dto.ProductDto;
import tr.com.obss.jip.finalproject.dto.SellerDto;
import tr.com.obss.jip.finalproject.request.ProductFilterRequest;

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
