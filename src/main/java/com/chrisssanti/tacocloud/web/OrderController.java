package com.chrisssanti.tacocloud.web;

import com.chrisssanti.tacocloud.data.jpa.OrderRepository;
import com.chrisssanti.tacocloud.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
@SessionAttributes("order")
@RequestMapping("/orders")
public class OrderController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm(){
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        orderRepository.save(order);
        log.info("Saved order: {}", order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
