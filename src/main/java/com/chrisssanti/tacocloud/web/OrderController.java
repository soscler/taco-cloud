package com.chrisssanti.tacocloud.web;

import com.chrisssanti.tacocloud.Utils.OrderProps;
import com.chrisssanti.tacocloud.data.jpa.OrderRepository;
import com.chrisssanti.tacocloud.model.Order;
import com.chrisssanti.tacocloud.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private OrderProps orderProps;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderProps orderProps){
        this.orderRepository = orderRepository;
        this.orderProps = orderProps;
    }

    @GetMapping("/current")
    public String orderForm(){
        return "orderForm";
    }

    /**
     * Use to get the last pageSize orders made by the connected user
     * Pageable is a Spring property that is used to deal with data (page ,pagesize)
     * @param user: will be fetch automatically in the session attributes
     * @param model
     * @return the view orderList
     */
    @GetMapping
    public String orderForUser(@AuthenticationPrincipal User user, Model model){
        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
        model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        // link the user who created the order to the order
        order.setUser(user);
        orderRepository.save(order);
        log.info("Saved order: {}", order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
