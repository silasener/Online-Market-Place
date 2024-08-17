package tr.com.obss.jip.finalproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.finalproject.dto.ProductDto;
import tr.com.obss.jip.finalproject.dto.SellerDto;
import tr.com.obss.jip.finalproject.request.CreateNewProductRequest;
import tr.com.obss.jip.finalproject.request.ProductFilterRequest;
import tr.com.obss.jip.finalproject.request.UpdateExistingProductRequest;
import tr.com.obss.jip.finalproject.response.BaseResponse;
import tr.com.obss.jip.finalproject.service.ProductService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products") //http://localhost:8080/api/v1/products
@RestController
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<BaseResponse<ProductDto>> createNewProduct(@RequestBody @Valid CreateNewProductRequest createNewProductRequest) {
        final ProductDto productDto = ProductDto.builder().id(UUID.randomUUID().toString())
                .name(createNewProductRequest.getName())
                .brand(createNewProductRequest.getBrand())
                .category(createNewProductRequest.getCategory())
                .build();
        ProductDto createdProduct = productService.createNewProduct(productDto);
        return ResponseEntity.ok(new BaseResponse<>(createdProduct));
    }


    @PreAuthorize("hasRole(('ROLE_ADMIN'))")
    @GetMapping()
    public BaseResponse<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtoList = productService.getAllProducts();
        return new BaseResponse<>(productDtoList);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{productId}")
    public BaseResponse<ProductDto> deleteProductById(@PathVariable("productId") String productId) {
        return new BaseResponse<>(productService.deleteProductById(productId));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{productId}")
    public BaseResponse<ProductDto> updateProductById(@PathVariable("productId") String productId, @RequestBody @Valid UpdateExistingProductRequest updateExistingProductRequest) {
        ProductDto productDto = ProductDto.builder().name(updateExistingProductRequest.getName())
                .category(updateExistingProductRequest.getCategory())
                .brand(updateExistingProductRequest.getBrand())
                .build();

        ProductDto updatedProduct = productService.updateExistingProduct(productId, productDto);
        return new BaseResponse<>(updatedProduct);
    }


    @GetMapping("/{productId}/sellers")
    public ResponseEntity<BaseResponse<List<SellerDto>>> getSellersByProductId(@PathVariable("productId") String productId) {
        List<SellerDto> sellersDto = productService.getSellersByProductId(productId);
        return ResponseEntity.ok(new BaseResponse<>(sellersDto));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<BaseResponse<ProductDto>> getProductById(@PathVariable("productId") String productId) {
        ProductDto productDto = productService.getProductById(productId);
        return ResponseEntity.ok(new BaseResponse<>(productDto));
    }

    @GetMapping("/by-seller")
    public ResponseEntity<List<ProductDto>> getProductsBySellerId(@RequestParam("sellerId") String sellerId) {
        List<ProductDto> products = productService.getProductsBySellerId(sellerId);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/available-product-filter")
    public ResponseEntity<BaseResponse<List<ProductDto>>> filterAvailableProductsForUser(@RequestBody ProductFilterRequest productFilterRequest) {
        List<ProductDto> filteredProducts = productService.filterAvailableProductsForUser(productFilterRequest);
        return ResponseEntity.ok(new BaseResponse<>(filteredProducts));
    }

    @GetMapping("/available-for-user")
    public ResponseEntity<BaseResponse<List<ProductDto>>> getAvailableProductsForUser(@RequestParam("userId") String userId) {
        List<ProductDto> availableProducts = productService.getAvailableProductsForUser(userId);
        return ResponseEntity.ok(new BaseResponse<>(availableProducts));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/filter")
    public ResponseEntity<BaseResponse<List<ProductDto>>> filterProducts(@RequestBody ProductFilterRequest productFilterRequest) {
        List<ProductDto> filteredProducts = productService.filterProducts(productFilterRequest);
        return ResponseEntity.ok(new BaseResponse<>(filteredProducts));
    }

}


