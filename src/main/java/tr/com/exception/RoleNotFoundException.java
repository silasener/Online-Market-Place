package tr.com.exception;

public class RoleNotFoundException extends BaseException {

    public RoleNotFoundException(String roleName) {
        super(String.format("Role  %s not found", roleName));
    }
}
