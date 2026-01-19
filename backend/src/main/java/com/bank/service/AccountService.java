package com.bank.service;

import com.bank.dto.TransferResponse;
import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository repo;

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    public Account credit(String id, double amount) {
        Account acc = getById(id);
        acc.balance += amount;
        return repo.save(acc);
    }

    public Account debit(String id, double amount) {
        Account acc = getById(id);
        acc.balance -= amount;
        return repo.save(acc);
    }

    public Account create(Account account) {
        return repo.save(account);
    }

    public Account update(Account account) {
        return repo.save(account);
    }

    public Account getById(String id) {
        return repo.findById(id).orElseThrow();
    }

    // ===== FUND TRANSFER (USER → USER) =====
    public TransferResponse transfer(String fromId, String toId, double amount) {

        if (fromId.equals(toId)) {
            throw new RuntimeException("SAME_ACCOUNT_TRANSFER_NOT_ALLOWED");
        }

        if (amount <= 0) {
            throw new RuntimeException("INVALID_TRANSFER_AMOUNT");
        }

        Account from = getById(fromId);
        Account to = getById(toId);

        if (from.balance < amount) {
            throw new RuntimeException("INSUFFICIENT_BALANCE");
        }

        from.balance -= amount;
        to.balance += amount;

        repo.save(from);
        repo.save(to);

        TransferResponse response = new TransferResponse();
        response.fromAccount = from;
        response.toAccount = to;
        response.status = "SUCCESS";

        return response;
    }
}
