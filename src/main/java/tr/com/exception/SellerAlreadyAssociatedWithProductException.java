package tr.com.exception;

public class SellerAlreadyAssociatedWithProductException extends BaseException {
    public SellerAlreadyAssociatedWithProductException(String sellerId) {
        super(String.format("Seller %s is already owns this product.", sellerId));
    }
}
