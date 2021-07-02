package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.tenmo.dao.UserDao;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.techelevator.tenmo.model.SendTransfer;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {
    private UserDao dao;


    public UserController(UserDao userDAO) {
        this.dao = userDAO;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return dao.findAll();
    }

    @RequestMapping(path = "/users/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable String username) {
        return dao.findByUsername(username);
    }

    @RequestMapping(path = "/users/{id}/balance", method = RequestMethod.GET)
    public BigDecimal getAccountBalance(@PathVariable long id, Principal principal) {
        return dao.getBalanceByUserid(id);
    }

    @RequestMapping(path = "/users/{id}/transfers", method = RequestMethod.GET)
    public List<Transfer> getTransferHistory(@PathVariable long id, Principal principal) {
        List<Transfer> transfers = new ArrayList<>();
        transfers = dao.getTransfersByUserId(id);
        return transfers;
    }

    @RequestMapping(path = "/users/{id}/transfers/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable long id, @PathVariable long transferId) {
        Transfer transfer = new Transfer();
        transfer = dao.getTransferDetailsWithTransferId(transferId);
        return transfer;
    }

    @RequestMapping(path = "/users/{id}/balance/withdraw", method = RequestMethod.PUT)
    public void updateUserAccountBalance(@PathVariable long id, @RequestBody SendTransfer transfer) {
        dao.updateUserBalanceAfterSentTransfer(id, transfer);
    }

    @RequestMapping(path = "/users/{id}/balance/deposit", method = RequestMethod.PUT)
    public void updateReceiverAccountBalance(@PathVariable long id, @RequestBody SendTransfer transfer) {
        dao.updateReceiversBalanceAfterTransfer(id, transfer);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/users/{id}/transfers/send", method = RequestMethod.POST)
    public int sendTenmoBucks(@PathVariable long id, @RequestBody SendTransfer transfer) {
        int results = dao.sendTransferToUser(id, transfer);
        return results;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/users/{id}/transfers/request", method = RequestMethod.POST)
    public int requestTenmoBucks(@PathVariable long id, @RequestBody SendTransfer transfer) {
        int results = dao.requestTransferFromUser(id, transfer);
        return results;
    }



}
