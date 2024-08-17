package tr.com.obss.jip.finalproject.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        UserAuthDetails userAuthDetails = (UserAuthDetails) authentication.getPrincipal();

        String role;
        if(userAuthDetails.getUsername().equals("admin")){ role="admin";
        }else{role="user";}

        UUID userUUID=userAuthDetails.getId();

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("role", role);
        responseBody.put("uuid", userUUID.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
    }
}
