package com.bank.manager.admin.controller;

import com.bank.manager.admin.dto.UserSummaryResponse;
import com.bank.manager.admin.service.AdminService;
import com.bank.manager.auth.model.User;
import com.bank.manager.security.annotations.IsAdmin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AdminService adminService;

    public AdminUserController(AdminService adminService) {
        this.adminService = adminService;
    }

    @IsAdmin
    @GetMapping
    public ResponseEntity<List<UserSummaryResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @IsAdmin
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @IsAdmin
    @PostMapping("/{id}/disable")
    public ResponseEntity<Void> disableUser(@PathVariable Long id) {
        adminService.disableUser(id);
        return ResponseEntity.ok().build();
    }

    @IsAdmin
    @PostMapping("/{id}/enable")
    public ResponseEntity<Void> enableUser(@PathVariable Long id) {
        adminService.enableUser(id);
        return ResponseEntity.ok().build();
    }

    @IsAdmin
    @PostMapping("/{id}/promote")
    public ResponseEntity<Void> promoteAdmin(@PathVariable Long id) {
        adminService.promoteToAdmin(id);
        return ResponseEntity.ok().build();
    }

    @IsAdmin
    @PostMapping("/{id}/demote")
    public ResponseEntity<Void> demoteAdmin(@PathVariable Long id) {
        adminService.demoteToUser(id);
        return ResponseEntity.ok().build();
    }

}