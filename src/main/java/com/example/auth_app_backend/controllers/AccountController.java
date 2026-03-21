package com.example.auth_app_backend.controllers;

import com.example.auth_app_backend.dtos.AccountMaster;
import com.example.auth_app_backend.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/accounts")
public class AccountController {
@Autowired
    AccountService service;

    // CREATE
    @PostMapping
    public AccountMaster create(@RequestBody AccountMaster account) {
        return service.create(account);
    }

    // GET ALL
    @GetMapping
    public List<AccountMaster> getAll() {
        return service.getAll();
    }
    // GET BY ID
    @GetMapping("/{id}")
    public AccountMaster getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public AccountMaster update(@PathVariable Long id, @RequestBody AccountMaster account) {
        return service.update(id, account);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Account deleted successfully";
    }
}
