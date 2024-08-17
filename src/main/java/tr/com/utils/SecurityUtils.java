package tr.com.obss.jip.finalproject.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@UtilityClass
public class SecurityUtils {

    public static String getCurrentUser() {
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            final Object authPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (authPrincipal instanceof UserDetails) {
                return ((UserDetails) authPrincipal).getUsername();
            }
            return authPrincipal.toString();
        }
        return StringUtils.EMPTY;
    }

}
