package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.SendTransfer;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private static final double STARTING_BALANCE = 1000;
    private JdbcTemplate jdbcTemplate;
    

    }

