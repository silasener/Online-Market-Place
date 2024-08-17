package tr.com.obss.jip.finalproject.exception;

public class ProductAlreadyExistingException  extends BaseException{
    public ProductAlreadyExistingException(String productId) {
        super("Product with ID " + productId + " already exists.");
    }
}
