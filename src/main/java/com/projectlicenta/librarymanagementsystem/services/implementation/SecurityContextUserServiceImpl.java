package com.projectlicenta.librarymanagementsystem.services.implementation;

import com.projectlicenta.librarymanagementsystem.model.entities.User;
import com.projectlicenta.librarymanagementsystem.repository.UserRepository;
import com.projectlicenta.librarymanagementsystem.services.SecurityContextUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityContextUserServiceImpl implements SecurityContextUserService {

    private final UserRepository userRepository;

    @Override
    public User getUserFromSecurityContext() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The user " + email + " was not found"));
    }
}
