package tr.com.obss.jip.finalproject.service;

import tr.com.obss.jip.finalproject.model.Role;

import java.util.List;

public interface RoleService {
    void checkAndCreateRoles(List<String> roleAdmin);

    Role findByName(String roleName);
}
