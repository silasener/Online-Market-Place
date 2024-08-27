package tr.com.exception;

public class SellerNotFoundException extends BaseException {
    public SellerNotFoundException(String sellerId) {
        super(String.format("Seller with id %s not found", sellerId));
    }

    public SellerNotFoundException() {
        super("No sellers found matching the provided vendorCode");
    }
}
