package com.bank.controller;

import com.bank.dto.CreateAccountRequest;
import com.bank.dto.TransferResponse;
import com.bank.model.Account;
import com.bank.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/open")
    public Account openAccount(@RequestBody CreateAccountRequest req) {
        Account acc = new Account();
        acc.id = req.id;
        acc.user = req.user;
        acc.balance = req.initialBalance;
        return service.create(acc);
    }

    @GetMapping("/balance/{id}")
    public Account balance(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping("/credit")
    public Account credit(
            @RequestParam String id,
            @RequestParam double amount) {
        return service.credit(id, amount);
    }

    @PostMapping("/debit")
    public Account debit(
            @RequestParam String id,
            @RequestParam double amount) {
        return service.debit(id, amount);
    }

    // ===== FUND TRANSFER (USER → USER) =====
    @PostMapping("/transfer")
    public TransferResponse transfer(
            @RequestParam String fromId,
            @RequestParam String toId,
            @RequestParam double amount) {

        return service.transfer(fromId, toId, amount);
    }
}
