package web.service;

import org.springframework.stereotype.Service;
import web.model.UserCustom;
import web.repo.RoleRopository;
import web.repo.UserRepository;

import java.util.List;

@Service
public class UserService {
    public UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserCustom> findAll(){
        return userRepository.findAll();
    }

    public UserCustom save(UserCustom userCustom) {
        return userRepository.save(userCustom);
    }

}
