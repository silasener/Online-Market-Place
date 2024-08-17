package tr.com.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;


    private static final String[] AUTH_WHITELIST = { //
            "/api/v1/h2-console", //
            "/api/v1/h2-console/**", //
            "/v3/api-docs/**", //
            "/swagger-ui/**", //
            "/swagger-ui.html", //
            "/swagger-ui/index.html", //
            "/api/v1/webjars/**", //
            "/api/v1/graphiql", //
            "/api/v1/api/graphql", //
    };

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder, UserAuthService userAuthService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(userAuthService);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(customizer -> customizer.requestMatchers(AUTH_WHITELIST).anonymous()
                        .anyRequest().hasAnyRole("ADMIN", "USER"))
                .exceptionHandling(customizer -> customizer
                        .accessDeniedHandler((req, resp, ex) -> resp.setStatus(HttpServletResponse.SC_FORBIDDEN))
                        .authenticationEntryPoint((req, resp, ex) -> resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED)))
                .formLogin(customizer -> customizer
                        .loginProcessingUrl("/login")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler((req, resp, ex) -> resp.setStatus(HttpServletResponse.SC_FORBIDDEN)))
                .sessionManagement(customizer -> customizer.invalidSessionStrategy((req, resp) -> resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED)))
                .logout(customizer -> customizer.logoutUrl("/logout").logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()))
                .csrf(customizer -> customizer.disable());

        return http.build();
    }


}
