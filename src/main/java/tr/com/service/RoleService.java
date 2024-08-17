package tr.com.service;

import tr.com.model.Role;

import java.util.List;

public interface RoleService {
    void checkAndCreateRoles(List<String> roleAdmin);

    Role findByName(String roleName);
}
