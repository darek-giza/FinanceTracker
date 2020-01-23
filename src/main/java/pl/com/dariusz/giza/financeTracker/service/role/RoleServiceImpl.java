package pl.com.dariusz.giza.financeTracker.service.role;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.user.Role;
import pl.com.dariusz.giza.financeTracker.repositories.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> setUserRole() {

        final List<Role> allRoles = roleRepository.findAll();

        return allRoles.stream()
                .filter(r -> r.getRole().contains("ROLE_USER"))
                .collect(Collectors.toList());

    }

    @Override
    public List<Role> setAdminRole() {

        final List<Role> allRoles = roleRepository.findAll();

        return allRoles.stream()
                .filter(r -> r.getRole().contains("ROLE_ADMIN"))
                .collect(Collectors.toList());
    }
}
