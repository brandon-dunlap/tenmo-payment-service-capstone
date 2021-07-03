package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

public interface AccountDao {

    int updateBalanceAfterTransfer(long userId, Transfer transfer);

    int updateBalanceReceiveTransfer(long userId, Transfer transfer);
}
