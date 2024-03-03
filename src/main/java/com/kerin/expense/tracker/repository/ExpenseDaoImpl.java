package com.kerin.expense.tracker.repository;

import com.kerin.expense.tracker.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Component
public class ExpenseDaoImpl implements ExpenseDao {

    @Autowired
    DataSource dataSource;

    JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Transaction save(Transaction transaction) {

        String query = "insert into expense (description,amount,type) values(?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, transaction.getDescription());
            preparedStatement.setDouble(2, transaction.getAmount());
            preparedStatement.setString(3, transaction.getType());
            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        Optional<Number> key = Optional.ofNullable(keyHolder.getKey());
        key.ifPresent(number -> transaction.setId(number.longValue()));

        return transaction;
    }

    @Override
    public Transaction findById(Long id) {
        return jdbcTemplate.queryForObject("select * from expense where id=?", new BeanPropertyRowMapper<>(Transaction.class),id);
    }

    @Override
    public List<Transaction> findAll() {
        return jdbcTemplate.query("Select * from expense", new BeanPropertyRowMapper<>(Transaction.class));
    }

    @Override
    public void deleteById(Long id) {
        String query = "delete from expense where id = ?";
        jdbcTemplate.update(query, id);
    }

    @Override
    public Long update(Transaction transaction) {
        String updateQuery = "update expense set description = ? , amount = ? , type = ? where id = ?";

        Object[] updateTransaction = new Object[]{transaction.getDescription(), transaction.getAmount(), transaction.getType() , transaction.getId()};

        jdbcTemplate.update(updateQuery,updateTransaction);

        return transaction.getId();
    }

}
