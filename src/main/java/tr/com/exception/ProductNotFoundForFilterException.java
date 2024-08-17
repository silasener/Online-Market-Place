package tr.com.exception;

public class ProductNotFoundForFilterException extends BaseException{
    public ProductNotFoundForFilterException() {
        super("No products found for the specified filter.");
    }
}
