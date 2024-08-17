package tr.com.exception;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String userId) {
        super("User with ID " + userId + " not found.");
    }

    public UserNotFoundException() {
        super("No users found matching the provided usernames.");
    }
}
