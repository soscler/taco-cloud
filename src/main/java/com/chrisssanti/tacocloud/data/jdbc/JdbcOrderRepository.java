package com.chrisssanti.tacocloud.data.jdbc;

import com.chrisssanti.tacocloud.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JdbcOrderRepository implements OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate =  jdbcTemplate;
    }

    @Override
    public Order save(Order order) {

        return order;
    }
}
