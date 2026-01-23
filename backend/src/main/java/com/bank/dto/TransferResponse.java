package com.bank.dto;

import com.bank.model.Account;

public class TransferResponse {
    public Account fromAccount;
    public Account toAccount;
    public String status;
}
