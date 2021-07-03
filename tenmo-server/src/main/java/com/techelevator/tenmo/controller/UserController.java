package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@RestController
//@PreAuthorize("isAuthenticated()")
public class UserController {
    @Autowired
    private JdbcUserDao userDao;
    @Autowired
    private JdbcTransferDao transferDao;




    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @RequestMapping(path = "/users/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable String username) {
        return userDao.findByUsername(username);
    }

    @RequestMapping(path = "/users/{id}/balance", method = RequestMethod.GET)
    public BigDecimal getAccountBalance(@PathVariable long id, Principal principal) {
        return userDao.getBalanceByUserid(id);
    }

    @RequestMapping(path = "/transfer/list", method = RequestMethod.GET)
    public List<Transfer> getTransferHistory(Principal principal) {
       // System.out.println("#######" + principal.getName());
        List<Transfer> transfers = new ArrayList<>();
        transfers = userDao.getTransfersByUserId(1001);
        return transfers;
    }

    @RequestMapping(path = "/users/{id}/transfers/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable long id, @PathVariable long transferId) {
        Transfer transfer = new Transfer();
        transfer = userDao.getTransferDetailsWithTransferId(transferId);
        return transfer;
    }

    @RequestMapping(path = "/users/{id}/balance/withdraw", method = RequestMethod.PUT)
    public void updateUserAccountBalance(@PathVariable long id, @RequestBody Transfer transfer) {
        transferDao.updateTransfer(transfer);
    }

    @RequestMapping(path = "/users/{id}/balance/deposit", method = RequestMethod.PUT)
    public void updateReceiverAccountBalance(@PathVariable long id, @RequestBody Transfer transfer) {
        transferDao.updateTransfer(transfer);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/users/{id}/transfers/send", method = RequestMethod.POST)
    public int sendTenmoBucks(@RequestBody Transfer transfer) {
         transferDao.addTransfer(transfer);
         return transfer.getTransferId();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/users/{id}/transfers/request", method = RequestMethod.POST)
    public int requestTenmoBucks(@RequestBody Transfer transfer) {
         transferDao.addTransfer(transfer);
        return transfer.getTransferId();
    }



}
