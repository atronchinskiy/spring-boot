package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import web.service.UserDetailsServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserDetailsServiceImpl userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(myPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
/*        http.authorizeRequests()
                .antMatchers("/main").hasRole("ADMIN")
                //.antMatchers("/main/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/**").permitAll()
                .and().formLogin()
                .and().httpBasic();*/
/*        http
            .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();

        http
                .authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/main").hasAnyRole("USER","ADMIN")
                .anyRequest().authenticated();*/
/*
        http
                .authorizeRequests()
                //.antMatchers("/", "/main").permitAll()
                .antMatchers("/main").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
*/
        http

                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()

                .authorizeRequests()
                    .antMatchers("/", "/main").permitAll()
    //                .antMatchers("/admin*").hasRole("ADMIN")
                    .antMatchers("/admin*").access("hasAuthority('ADMIN')")
                    .anyRequest().authenticated()
                    .and()

                .logout()
                    .permitAll();
    }

    @Bean
    public PasswordEncoder myPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}