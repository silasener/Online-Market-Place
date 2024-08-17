package tr.com.obss.jip.finalproject.exception;

public class RoleNotFoundException extends BaseException {

    public RoleNotFoundException(String roleName) {
        super(String.format("Role  %s not found", roleName));
    }
}
