package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.SendTransfer;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {

    List<User> findAll();

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);

    BigDecimal getBalanceByUserid(long id);


    List<Transfer> getTransfersByUserId(long id);

    Transfer getTransferDetailsWithTransferId(long transferId);

    void updateUserBalanceAfterSentTransfer(long id, SendTransfer transfer);

    void updateReceiversBalanceAfterTransfer(long id, SendTransfer transfer);

    int sendTransferToUser(long id, SendTransfer transfer);

    int requestTransferFromUser(long id, SendTransfer transfer);
}
