package com.techelevator.tenmo.model;

public class TransferTypes {

    private int transferTypeId;
    private String transferTypeDescription;

    public TransferTypes(int transferTypeId, String transferTypeDescription) {
        this.transferTypeId = transferTypeId;
        this.transferTypeDescription = transferTypeDescription;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public String getTransferTypeDescription() {
        return transferTypeDescription;
    }

    public void setTransferTypeDescription(String transferTypeDescription) {
        this.transferTypeDescription = transferTypeDescription;
    }
}
