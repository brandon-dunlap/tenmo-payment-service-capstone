package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.SendTransfer;
import com.techelevator.tenmo.model.Transfer;

import java.util.List;


public interface TransferDao {

    Transfer getTransferById(long transferId);

    Transfer addTransfer(Transfer newTransfer);

    List<Transfer> sentTransfers(long userFrom);

    List<Transfer> receivedTransfers(long userTo);

    List<Transfer> transfersById(long id);

    public int transferToUser(long userFrom, SendTransfer transfer);

    public int requestToUser(long userFrom, SendTransfer transfer);


}
