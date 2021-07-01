package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    private int accountId;
    private int userId;
    private BigDecimal balance;

    public Account(int accountId, int userId, BigDecimal balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Reduces the balance of this account and increases the balance of accountTo by amount specified in amountToTransfer parameter
     * @param accountTo
     * @param amountToTransfer
     */
    public void transfer(Account accountTo, BigDecimal amountToTransfer) {
        if(this.balance.compareTo(amountToTransfer) >= 0) {
            this.balance = this.balance.subtract(amountToTransfer);
            accountTo.balance = accountTo.balance.add(amountToTransfer);
        } else {
            throw new InsufficientBalanceException(amountToTransfer+" exceeds the remaining balance of "+this.balance);
        }
    }


}
