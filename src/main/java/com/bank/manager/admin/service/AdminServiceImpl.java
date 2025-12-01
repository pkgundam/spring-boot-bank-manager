package com.bank.manager.admin.service;

import com.bank.manager.admin.dto.UserSummaryResponse;
import com.bank.manager.auth.entity.UserEntity;
import com.bank.manager.auth.entity.enums.Role;
import com.bank.manager.auth.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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
                        u.getEnabled()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void disableUser(Long id) {
        UserEntity user = getUserById(id);
        user.setEnabled(false);
        userRepo.save(user);
    }

    @Override
    public void enableUser(Long id) {
        UserEntity user = getUserById(id);
        user.setEnabled(true);
        userRepo.save(user);
    }

    @Override
    public void promoteToAdmin(Long id) {
        UserEntity user = getUserById(id);
        user.getRoles().add(Role.ADMIN);
        userRepo.save(user);
    }

    @Override
    public void demoteToUser(Long id) {
        UserEntity user = getUserById(id);
        user.getRoles().remove(Role.ADMIN);
        userRepo.save(user);
    }

}