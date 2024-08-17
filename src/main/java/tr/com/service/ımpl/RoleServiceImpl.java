package tr.com.obss.jip.finalproject.service.ımpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.finalproject.exception.RoleNotFoundException;
import tr.com.obss.jip.finalproject.model.Role;
import tr.com.obss.jip.finalproject.repository.RoleRepository;
import tr.com.obss.jip.finalproject.service.RoleService;
import tr.com.obss.jip.finalproject.utils.CollectionUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public void checkAndCreateRoles(List<String> roles) {
        if (CollectionUtils.isEmpty(roles)) {
            return;
        }
        roles.forEach(role -> {
            roleRepository.findByName(role).orElseGet(() -> roleRepository.save(new Role(role)));
        });
    }

    @Override
    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() -> new RoleNotFoundException(roleName));
    }

}
