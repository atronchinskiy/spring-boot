package web.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import web.model.Role;
import web.model.UserCustom;
import web.service.UserService;

@Component
public class SecurityHandler implements AuthenticationSuccessHandler {

    public final UserService userService;

    public SecurityHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        UserCustom user;
        user = null;
        Set<String> rolesList = new HashSet<>();
        try {
            user = userService.findByName(httpServletRequest.getParameter("username"));
        } catch (Exception e) {

        }

        if (user != null) {
            for (Role role : user.getRoles()) {
                rolesList.add(role.getRole());
            }
        } else {
            httpServletResponse.sendRedirect("/login");
        }

        if (rolesList.contains("ADMIN")) {
            httpServletResponse.sendRedirect("/admin");
        } else {
            httpServletResponse.sendRedirect("/user");
        }
    }
}
