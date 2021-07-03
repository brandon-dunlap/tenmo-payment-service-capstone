package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Transfer;

import java.util.List;


public interface TransferDao {

    Transfer getTransferById(long transferId);

    void addTransfer(Transfer newTransfer);

    List<Transfer> getTransfersByUserId(long id);

    void updateTransfer(Transfer transfer);




}
