package com.projectlicenta.librarymanagementsystem.security.utils;

import com.projectlicenta.librarymanagementsystem.model.entities.User;
import com.projectlicenta.librarymanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userResult = userRepository.findUserByEmail(email);
        if (userResult.isEmpty()) {
            throw new UsernameNotFoundException("User with email " + email + " could not be found");
        }
        User user = userResult.get();

        List<GrantedAuthority> authorities = user.getRoleList().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toUpperCase()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User
                (email, user.getCredential().getPassword(),
                        authorities);
    }
}
