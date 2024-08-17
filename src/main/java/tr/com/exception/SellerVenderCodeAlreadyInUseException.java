package tr.com.obss.jip.finalproject.exception;

public class SellerVenderCodeAlreadyInUseException extends BaseException{
    public SellerVenderCodeAlreadyInUseException(String venderCode) {
        super("Vender code " + venderCode + " is already in use by another seller.");
    }
}
