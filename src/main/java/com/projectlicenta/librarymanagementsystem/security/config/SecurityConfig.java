package com.projectlicenta.librarymanagementsystem.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers(
                        "/login/**",
                        "/register/**", "/CSS/**", "/JS/**", "/logout/**", "/HTML/**","/Images/**").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/employee/**").hasRole("LIBRARIAN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/**").authenticated().and().formLogin()
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                    if (roles.contains("ROLE_USER")) {
                        response.sendRedirect("/user/");
                    } else if (roles.contains("ROLE_LIBRARIAN")) {
                        response.sendRedirect("/employee/");
                    } else if (roles.contains("ROLE_ADMIN")) {
                        response.sendRedirect("/admin/");
                    }
                })
                .failureHandler(authenticationFailureHandler())
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
}
