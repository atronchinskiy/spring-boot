package web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.UserCustom;
import web.repo.RoleRopository;
import web.repo.UserRepository;
import java.util.*;

@Controller
public class AdminController {

    private RoleRopository roleService;
    private UserRepository userService;

    public AdminController(RoleRopository roleService, UserRepository userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.GET
            )
    public String getPage(ModelMap modelMap, @AuthenticationPrincipal User user) {

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.addAll(user.getAuthorities());

        for (GrantedAuthority g : grantedAuthorities) {
            if (g.getAuthority().equals("ADMIN")){
                return "redirect:/admin";
            }
        }
        return "redirect:/user";
    }

    @GetMapping("/admin")
    public String getAdminPage(ModelMap modelMap, @AuthenticationPrincipal User user) {
        List<UserCustom> userList = userService.findAll();
        modelMap.addAttribute("users", userList);
        return "admin";
    }


    @PostMapping("/admin")
    public String adminPagePost(ModelMap modelMap,
                                @RequestParam("name") String name,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam(value = "admin", required = false, defaultValue = "") String admin,
                                @RequestParam(value = "user", required = false, defaultValue = "") String user) {

        Set<Role> roleSet = new HashSet<>();
        if (admin.equals("ROLE_ADMIN")) {
            roleSet.add(roleService.getOne(1L));
        }
        if (user.equals("ROLE_USER")) {
            roleSet.add(roleService.getOne(2L));
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        userService.save(new UserCustom(name, lastName, email, hashedPassword, roleSet));
        return "redirect:/admin";
    }
}
