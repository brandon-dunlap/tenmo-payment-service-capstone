package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    public static String AUTH_TOKEN = "";
    private final String BASE_URL;
    public RestTemplate restTemplate = new RestTemplate();

    public AccountService(String url) {
        BASE_URL = url;
    }

    public BigDecimal getBalance(AuthenticatedUser user) {
        AUTH_TOKEN = user.getToken();
        BigDecimal balance = new BigDecimal("0.00");
        Account account = new Account();
        try {
            account = restTemplate.exchange(BASE_URL + "account/balance", HttpMethod.GET, makeAccountEntity(user), Account.class).getBody();
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return account.getAccountBalance();
    }

    public User[] getAllUsers(AuthenticatedUser user) {
        AUTH_TOKEN = user.getToken();
        User[] users = null;
        try {
            users = restTemplate.exchange(BASE_URL + "account/finduser", HttpMethod.GET, makeAuthEntity(), User[].class)
                    .getBody();
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return users;
    }

    public void sendTransfer(AuthenticatedUser user, Transfer transfer) {
        AUTH_TOKEN = user.getToken();
        try {
            restTemplate.exchange(BASE_URL + "account/send", HttpMethod.PUT, makeTransferEntity(transfer), Transfer.class);
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
    }

    public void requestTransfer(AuthenticatedUser user, Transfer transfer) {
        AUTH_TOKEN = user.getToken();
        try {
            restTemplate.exchange(BASE_URL + "account/request", HttpMethod.PUT, makeTransferEntity(transfer),
                    Transfer.class);
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
    }

    public Transfer[] getTransferHistory(AuthenticatedUser user) {
        AUTH_TOKEN = user.getToken();
        Transfer[] transfers = null;
        try {
            transfers = restTemplate.exchange(BASE_URL + "account/transfers/history", HttpMethod.GET, makeAuthEntity(),
                    Transfer[].class).getBody();
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return transfers;
    }

    public Transfer[] listTransfers (String authToken) {
        Transfer[] transfers;

        transfers = restTemplate.exchange(BASE_URL + "/transfer/list", HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();

        return transfers;

    }

    public Transfer[] getTransferById (AuthenticatedUser user) {
        AUTH_TOKEN = user.getToken();
        Transfer[] transfers =  null;
        try {
            transfers = restTemplate.exchange(BASE_URL + "/transfer/{id}", HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return transfers;

    }

    private HttpEntity<AuthenticatedUser> makeAccountEntity(AuthenticatedUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity<AuthenticatedUser> entity = new HttpEntity<>(user, headers);
        return entity;
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }

    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}