package com.techelevator.tenmo.model;

public class TransferStatuses {

    private int transferStatusId;
    private String transferStatusDescription;

    public TransferStatuses(int transferTypeId, String transferTypeDescription) {
        this.transferStatusId = transferTypeId;
        this.transferStatusDescription = transferTypeDescription;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public String getTransferStatusDescription() {
        return transferStatusDescription;
    }

    public void setTransferStatusDescription(String transferStatusDescription) {
        this.transferStatusDescription = transferStatusDescription;
    }
}
