package com.bank.manager.admin.service;

import com.bank.manager.admin.dto.UserSummaryResponse;
import com.bank.manager.auth.model.User;

import java.util.List;

public interface AdminService {

    List<UserSummaryResponse> getAllUsers();

    User getUserById(Long id);

    void disableUser(Long id);

    void enableUser(Long id);

    void promoteToAdmin(Long id);

    void demoteToUser(Long id);

}