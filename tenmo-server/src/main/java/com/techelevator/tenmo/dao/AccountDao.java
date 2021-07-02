package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.SendTransfer;

public interface AccountDao {

    int updateBalanceAfterTransfer(long userId, SendTransfer transfer);

    int updateBalanceReceiveTransfer(long userId, SendTransfer transfer);
}
