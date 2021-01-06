package com.dawid.services;

import com.dawid.commands.OrderCommand;
import com.dawid.converters.OrderCommandToCommand;
import com.dawid.converters.OrderToOrderCommand;
import com.dawid.domain.Order;
import com.dawid.domain.User;
import com.dawid.repositories.OrderRepository;
import com.dawid.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderCommandToCommand orderCommandToCommand;
    private final OrderToOrderCommand orderToOrderCommand;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderCommandToCommand orderCommandToCommand, OrderToOrderCommand orderToOrderCommand, OrderRepository orderRepository, UserRepository userRepository) {
        this.orderCommandToCommand = orderCommandToCommand;
        this.orderToOrderCommand = orderToOrderCommand;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveOrderCommand(OrderCommand orderCommand) {
        Order order = orderCommandToCommand.convert(orderCommand);
        orderCommand.getProducts().forEach(productCommand -> order.setPrice(order.getPrice()+productCommand.getPrice()));
        order.setDate(new Date());
        orderRepository.save(order);

    }

    @Override
    public Set<OrderCommand> getUserOrders(Long id) {
        User user = userRepository.findById(id).get();
        Set<Order> userOrders = orderRepository.findByUserInfo(user);
        Set<OrderCommand> userOrdersCommand = new HashSet<>();
        userOrders.forEach(order -> userOrdersCommand.add(orderToOrderCommand.convert(order)));
        return userOrdersCommand;

    }

    @Override
    public Set<Order> getOrders() {
        Set<Order> orders = new TreeSet<>(Comparator.comparing(Order::getId));
        orderRepository.findAll().stream().iterator().forEachRemaining(orders::add);
        return orders;
    }
}
