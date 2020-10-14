package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.UserCustom;
import web.repo.UserRepository;

import java.util.Map;
import java.util.Optional;

@Controller
public class AdminController1 {

/*    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String _main(Map<String, Object> model) {
        Iterable<UserCustom> users= userRepository.findAll();
        Optional<UserCustom> us = userRepository.findById(1L);
        UserCustom u = userRepository.findFirstByEmail("q");
        boolean b = userRepository.existsById(1L);
        model.put("users", users);
        return "main";
    }*/

/*    @GetMapping("/login")
    public String login() {
        return "/login";
    }*/

 /*   @GetMapping("/main")
    public String main(Map<String, Object> model) {
        return "main";
    }*/

}
