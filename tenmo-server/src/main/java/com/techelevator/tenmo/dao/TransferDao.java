package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;


public interface TransferDao {

    Transfer getTransferById(int transferId);

    Transfer addTransfer(Transfer newTransfer);

    List<Transfer> getTransfersForUser(int userId);

    List<Transfer> findAll();

    List<Transfer> getPendingTransferForUser(int currentUserId);

    void updateStatus(Transfer transfer);
}
