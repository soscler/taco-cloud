package com.chrisssanti.tacocloud.data;

import com.chrisssanti.tacocloud.model.Order;

public interface OrderRepository {
    Order save(Order order);
}
