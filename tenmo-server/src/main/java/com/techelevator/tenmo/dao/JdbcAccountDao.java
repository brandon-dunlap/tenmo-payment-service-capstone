package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;



public abstract class JdbcAccountDao implements AccountDao {
    private static final double STARTING_BALANCE = 1000;
    private JdbcTemplate jdbcTemplate;

    public int updateBalanceAfterSent(long userId, Transfer transfer) {
        String sqlUpdateBalance = "UPDATE accounts SET balance = balance - ?"
                                    + "WHERE user_id = ?";

        int results = jdbcTemplate.update(sqlUpdateBalance, transfer.getAmount(), userId);
        return results;
    }

    public int updateBalanceReceived(long userId, Transfer transfer) {
        String sqlUpdateBalance = "UPDATE accounts SET balance = balance + ?"
                                    + "WHERE user_id = ?";

        int results = jdbcTemplate.update(sqlUpdateBalance, transfer.getAmount(), userId);
        return results;
    }



}

