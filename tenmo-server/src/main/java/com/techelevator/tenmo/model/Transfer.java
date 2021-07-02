package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private String transferType;
    private String transferStatus;
    private String userFrom;
    private String userTo;
    private BigDecimal amount;

    public static final String TRANSFER_TYPE_REQUEST = "Request";
    public static final String TRANSFER_TYPE_SEND = "Send";
    public static final String TRANSFER_TYPE_PENDING = "Pending";
    public static final String TRANSFER_TYPE_APPROVED = "Approved";
    public static final String TRANSFER_TYPE_REJECTED = "Rejected";

    public Transfer(int transferId, String transferType, String transferStatus, String userFrom, String userTo, BigDecimal amount) {
        this.transferId = transferId;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = amount;
    }

    public Transfer() {

    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isApproved() {
        return TRANSFER_TYPE_APPROVED.equals(this.transferStatus);
    }

    public boolean isRejected() {
        return TRANSFER_TYPE_REJECTED.equals(this.transferStatus);
    }

    public boolean isPending() {
        return TRANSFER_TYPE_PENDING.equals(this.transferStatus);
    }

    public boolean isRequestType() {
        return TRANSFER_TYPE_REQUEST.equals(this.transferType);
    }

    public boolean isSendType() {
        return TRANSFER_TYPE_SEND.equals(this.transferType);
    }



    }

