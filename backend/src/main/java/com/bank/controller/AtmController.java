package com.bank.controller;

import com.bank.model.Account;
import com.bank.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/atm")
public class AtmController {

    private final AccountService service;

    public AtmController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/withdraw")
    public Account withdraw(
            @RequestParam String id,
            @RequestParam double amount) {

        Account acc = service.getById(id);

        if (acc.balance < amount) {
            throw new RuntimeException("INSUFFICIENT_BALANCE");
        }

        acc.balance -= amount;
        return service.update(acc);
    }
}
