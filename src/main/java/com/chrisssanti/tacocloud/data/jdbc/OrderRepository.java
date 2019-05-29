package com.chrisssanti.tacocloud.data.jdbc;

import com.chrisssanti.tacocloud.model.Order;

public interface OrderRepository {
    Order save(Order order);
}
