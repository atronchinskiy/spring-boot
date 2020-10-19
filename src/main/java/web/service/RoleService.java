package web.service;

import org.springframework.stereotype.Service;
import web.model.Role;
import web.repo.RoleRopository;

@Service
public class RoleService {
    public RoleRopository roleRopository;

    public RoleService(RoleRopository roleRopository) {
        this.roleRopository = roleRopository;
    }

    public Role getOne(Long l) {
        return roleRopository.getOne(l);
    }
}
