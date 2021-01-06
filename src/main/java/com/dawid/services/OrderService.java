package com.dawid.services;

import com.dawid.commands.OrderCommand;
import com.dawid.domain.Order;

import java.util.Set;

public interface OrderService {

    void saveOrderCommand(OrderCommand orderCommand);

    Set<OrderCommand> getUserOrders(Long id);

    Set<Order> getOrders();
}
