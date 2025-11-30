package com.bank.manager.admin.controller;

import com.bank.manager.dto.AccountResponse;
import com.bank.manager.security.annotations.CurrentUser;
import com.bank.manager.security.annotations.IsAdmin;
import com.bank.manager.security.jwt.CustomUserDetails;
import com.bank.manager.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/accounts")
public class AdminAccountController {

    private final AccountService accountService;

    public AdminAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @IsAdmin
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts(@CurrentUser CustomUserDetails userDetails) {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

}