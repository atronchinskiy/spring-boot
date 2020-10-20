package web;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.UserCustom;
import web.service.RoleService;
import web.service.UserService;

import java.util.*;

@Controller
public class AdminController {

    private RoleService roleService;
    private UserService userService;

    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
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

    @PostMapping("/delete")
    public String deletePagePost(ModelMap modelMap, @RequestParam("name") String name) {
        userService.deleteUserCustom(name);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String userPageGet(ModelMap modelMap) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserCustom userCustom = userService.findByName(user.getUsername());
        modelMap.addAttribute("user", userCustom);
        return "user";
    }

    @GetMapping("/admin/edit")
    public String editPageGet(ModelMap modelMap, @RequestParam("name") String name) {
        UserCustom userCustom = userService.findByName(name);
        List<String> roleArr = new ArrayList<>();
        String roleUser = null;
        String roleAdmin = null;
        for (Role r : userCustom.getRoles()) {
            if (r.getRole().equals("USER")) {
                roleUser = "ROLE_USER";
            } else if (r.getRole().equals("ADMIN")) {
                roleAdmin = "ROLE_ADMIN";
            }
        }
        modelMap.addAttribute("user", userCustom)
                .addAttribute("roleAdmin", roleAdmin)
                .addAttribute("roleUser", roleUser);
        return "edit";
    }

    @PostMapping("/admin/edit")
    public String editPagePost(ModelMap modelMap,
                               @RequestParam("id") String id,
                               @RequestParam("name") String name,
                               @RequestParam("lastName") String lastName,
                               @RequestParam("email") String email,
                               @RequestParam("password") String passsword,
                               @RequestParam(value = "admin", required = false, defaultValue = "") String admin,
                               @RequestParam(value = "user", required = false, defaultValue = "") String user) {
        Set<Role> roleSet = new HashSet<>();
        if (user.equals("on")) {
            roleSet.add(roleService.getOne(2L));
        }
        if (admin.equals("on")) {
            roleSet.add(roleService.getOne(1L));
        }
        UserCustom updateUser = new UserCustom((long) Integer.parseInt(id), name, lastName, email, passsword, roleSet);
        userService.save(updateUser);
        return "redirect:/admin";
    }
}
