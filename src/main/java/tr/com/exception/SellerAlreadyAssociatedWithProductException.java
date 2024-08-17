package tr.com.obss.jip.finalproject.exception;

public class SellerAlreadyAssociatedWithProductException extends BaseException {
    public SellerAlreadyAssociatedWithProductException(String sellerId) {
        super(String.format("Seller %s is already owns this product.", sellerId));
    }
}
