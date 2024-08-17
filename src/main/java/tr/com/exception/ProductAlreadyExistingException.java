package tr.com.exception;

public class ProductAlreadyExistingException  extends BaseException{
    public ProductAlreadyExistingException(String productId) {
        super("Product with ID " + productId + " already exists.");
    }
}
