package com.techelevator.tenmo.dao;



import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private static final double STARTING_BALANCE = 1000;
    private JdbcTemplate jdbcTemplate;


    @Override
    public Transfer getTransferById(long transferId) {
    String sql = "SELECT * FROM transfers WHERE transfer_id = ?";
    Transfer transfer = jdbcTemplate.queryForObject(sql, Transfer.class, transferId);
        return transfer;
    }

    @Override
    public void addTransfer(Transfer newTransfer) {
        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)\n" +
                "Values (?, ?, ?, ?, ?) " ;
        jdbcTemplate.update(sql, newTransfer.getTransferType(), newTransfer.getTransferStatus(), newTransfer.getAccountFrom(), newTransfer.getAccountTo(), newTransfer.getAmount());
    }

    @Override
    public List<Transfer> getTransfersByUserId(long id) {
        String sql = "SELECT * FROM transfers \n" +
                "WHERE account_from IN (SELECT account_id FROM accounts WHERE user_id = ?)\n" +
                "OR account_to IN (SELECT account_id FROM accounts WHERE user_id = ?)";
        List<Transfer> transfers = jdbcTemplate.queryForList(sql, Transfer.class, id, id);
        return transfers;

    }

    @Override
    public void updateTransfer(Transfer transfer) {
        String sql = "UPDATE transfers SET transfer_status_id = ? WHERE transfer_id = ?";
        jdbcTemplate.update(sql, transfer.getTransferStatus(), transfer.getTransferId());
        if(transfer.getTransferStatus() == 2) {
            String balanceSql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
            jdbcTemplate.update(balanceSql, transfer.getAmount(), transfer.getAccountTo());
             balanceSql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
            jdbcTemplate.update(balanceSql, transfer.getAmount(), transfer.getAccountFrom());
        }

    }


}


