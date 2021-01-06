package com.dawid.repositories;

import com.dawid.domain.Order;
import com.dawid.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Set<Order> findByUserInfo(User user);

}
