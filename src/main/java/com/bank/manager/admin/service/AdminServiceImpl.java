package com.bank.manager.admin.service;

import com.bank.manager.admin.dto.UserSummaryResponse;
import com.bank.manager.auth.Role;
import com.bank.manager.auth.model.User;
import com.bank.manager.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepo;

    public AdminServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<UserSummaryResponse> getAllUsers() {
        return userRepo.findAll().stream()
                .map(u -> new UserSummaryResponse(
                        u.getId(),
                        u.getEmail(),
                        u.getFullName(),
                        u.getRoles(),
                        true // enabled (you can expand later)
                ))
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void disableUser(Long id) {
        User user = getUserById(id);
        user.setEnabled(false);
        userRepo.save(user);
    }

    @Override
    public void enableUser(Long id) {
        User user = getUserById(id);
        user.setEnabled(true);
        userRepo.save(user);
    }

    @Override
    public void promoteToAdmin(Long id) {
        User user = getUserById(id);
        user.getRoles().add(Role.ADMIN);
        userRepo.save(user);
    }

    @Override
    public void demoteToUser(Long id) {
        User user = getUserById(id);
        user.getRoles().remove(Role.ADMIN);
        userRepo.save(user);
    }

}