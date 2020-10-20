package web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.model.UserCustom;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserCustom, Long> {
    public UserCustom findFirstByEmail(String e);

    public List<UserCustom> findAllBy();

    public UserCustom findByName(String string);

    public void deleteUserCustomByName(String name);

}
