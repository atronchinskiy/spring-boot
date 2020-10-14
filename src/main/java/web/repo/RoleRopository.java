package web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.Role;

@Repository
public interface RoleRopository extends JpaRepository <Role, Long> {

}
