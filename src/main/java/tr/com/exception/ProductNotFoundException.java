package tr.com.exception;

public class ProductNotFoundException extends BaseException {
    public ProductNotFoundException(String productId) {
        super("Product with ID " + productId + " not found.");
    }
}
