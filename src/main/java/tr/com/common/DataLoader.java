package tr.com.common;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import tr.com.service.ProductService;
import tr.com.service.RoleService;
import tr.com.service.UserService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class DataLoader implements ApplicationRunner {

    private final UserService userService;

    private final RoleService roleService;

    private final ProductService productService;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws IOException {
        roleService.checkAndCreateRoles(List.of(Constants.Roles.ADMIN, Constants.Roles.USER));
        userService.checkAndCreateAdminUser();
        productService.checkAndLoadSqlData();
    }
}
