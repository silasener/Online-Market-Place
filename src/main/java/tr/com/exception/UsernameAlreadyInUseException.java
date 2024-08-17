package tr.com.exception;

public class UsernameAlreadyInUseException extends BaseException{
    public UsernameAlreadyInUseException(String username) {
        super("Username '" + username + "' is already in use.");
    }
}
