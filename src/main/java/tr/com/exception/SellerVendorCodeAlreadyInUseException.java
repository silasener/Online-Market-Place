package tr.com.exception;

public class SellerVendorCodeAlreadyInUseException extends BaseException{
    public SellerVendorCodeAlreadyInUseException(String vendorCode) {
        super("Vendor code " + vendorCode + " is already in use by another seller.");
    }
}
