package com.bank.manager.security.jwt;

import com.bank.manager.auth.model.User;
import com.bank.manager.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return new CustomUserDetails(user);
    }

    public CustomUserDetails loadUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return new CustomUserDetails(user);
    }

}