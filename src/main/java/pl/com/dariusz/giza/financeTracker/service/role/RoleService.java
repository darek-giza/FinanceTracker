package pl.com.dariusz.giza.financeTracker.service.role;

import pl.com.dariusz.giza.financeTracker.domain.user.Role;

import java.util.List;

public interface RoleService {

    List<Role> setUserRole();

    List<Role> setAdminRole();

}
