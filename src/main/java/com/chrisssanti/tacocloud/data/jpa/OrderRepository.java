package com.chrisssanti.tacocloud.data.jpa;

import com.chrisssanti.tacocloud.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findOrderByDeliveryZip(String zip);

    List<Order> findOrderByDeliveryZipAndPlacedAtBetween(String zip, Date startDate, Date endDate);

    /*@Query(value = "select o from Taco_Order where o.deliveryCity='Seattle'")
    List<Order> readOrdersDeliveredInSeattle();*/

}
