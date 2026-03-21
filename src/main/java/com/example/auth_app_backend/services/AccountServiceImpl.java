package com.example.auth_app_backend.services;

import com.example.auth_app_backend.dtos.AccountMaster;
import com.example.auth_app_backend.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public class AccountServiceImpl implements  AccountService{
    @Autowired
    AccountRepository repository;
    @Override
    public AccountMaster create(AccountMaster account) {
        account.setCreateAt(Instant.from(LocalDateTime.now()));
        account.setUpdateAt(Instant.from(LocalDateTime.now()));
        return repository.save(account);
    }

    @Override
    public List<AccountMaster> getAll() {
        return repository.findAll();
    }

    @Override
    public AccountMaster getById(Long id) {
        return repository.findById(String.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Override
    public AccountMaster update(Long id, AccountMaster updated) {
        AccountMaster existing = getById(id);

        existing.setFullname(updated.getFullname());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setCompany(updated.getCompany());
        existing.setActive(updated.isActive());
        existing.setHasTwoStepAuthencation(updated.isHasTwoStepAuthencation());
        existing.setLogo(updated.getLogo());
        existing.setUpdateBy(updated.getUpdateBy());
        existing.setUpdateAt(Instant.from(LocalDateTime.now()));

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(String.valueOf(id));
    }
}
