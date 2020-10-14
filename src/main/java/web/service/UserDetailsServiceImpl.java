package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.UserCustom;
import web.repo.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCustom userCustom = null;
        String[] roleArray = new String[2];
        Set<GrantedAuthority> roles = new HashSet();

        try {
            userCustom = userRepository.findByName(username);
            int i = 0;
            for (Role role: userCustom.getRoles())
            {
                roleArray[i] = role.getRole();
                i++;
                roles.add(new SimpleGrantedAuthority(role.getRole()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*UserDetails user = User.builder()
                .username(userCustom.getUsername())
                .password(userCustom.getPassword())
                .roles(roleArray)
                .build();*/

        UserDetails user = new org.springframework.security.core.userdetails.User(userCustom.getUsername(),
                        userCustom.getPassword(), roles);
        return user;
    }
}

