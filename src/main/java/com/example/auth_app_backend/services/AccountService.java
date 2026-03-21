package com.example.auth_app_backend.services;

import com.example.auth_app_backend.dtos.AccountMaster;

import java.util.List;

public interface AccountService {
    AccountMaster create(AccountMaster account);

    List<AccountMaster> getAll();

    AccountMaster getById(Long id);

    AccountMaster update(Long id, AccountMaster account);

    void delete(Long id);
}

