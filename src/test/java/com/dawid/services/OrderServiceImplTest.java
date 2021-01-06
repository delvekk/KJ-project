package com.dawid.services;

import com.dawid.commands.OrderCommand;
import com.dawid.converters.OrderCommandToCommand;
import com.dawid.converters.OrderToOrderCommand;
import com.dawid.domain.Order;
import com.dawid.domain.User;
import com.dawid.repositories.OrderRepository;
import com.dawid.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    OrderServiceImpl orderService;

    @Mock
    OrderCommandToCommand orderCommandToCommand;

    @Mock
    OrderToOrderCommand orderToOrderCommand;

    @Mock
    UserRepository userRepository;

    @Mock
    OrderRepository orderRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        orderService = new OrderServiceImpl(orderCommandToCommand, orderToOrderCommand,orderRepository, userRepository);

    }

    @Test
    public void saveOrderCommand() {
        OrderCommand orderCommand = new OrderCommand();
        orderCommand.setId(1L);

        Order order = new Order();
        order.setId(1L);

        when(orderCommandToCommand.convert(any())).thenReturn(order);

        orderService.saveOrderCommand(orderCommand);

        verify(orderRepository, times(1)).save(any());

    }

    @Test
    public void getUserOrders() {
        Long id = 1L;

        User user = new User();
        user.setId(1L);

        Order order = new Order();
        Set<Order> orders = new HashSet<>();
        orders.add(order);


        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(orderRepository.findByUserInfo(any())).thenReturn(orders);



        Set<OrderCommand> orderCommands = orderService.getUserOrders(id);

        assertEquals(1, orderCommands.size());
        verify(orderToOrderCommand, times(1)).convert(any());
        verify(orderRepository, times(1)).findByUserInfo(any());
        verify(userRepository, times(1)).findById(anyLong());

    }

    @Test
    public void getOrders() {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        order.setId(1L);

        Order order1 = new Order();
        order1.setId(2L);

        orderList.add(order);
        orderList.add(order1);

        when(orderRepository.findAll()).thenReturn(orderList);

        Set<Order> orders = orderService.getOrders();

        assertEquals(2, orders.size());

    }

}