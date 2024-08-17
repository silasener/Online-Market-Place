package tr.com.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.com.model.User;
import tr.com.service.UserService;

@RequiredArgsConstructor
@Service
public class UserAuthService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userService.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserAuthDetails(user);
    }
}
