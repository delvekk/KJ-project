package com.dawid.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "orders")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer price = 0;
    @ManyToOne
    @JoinTable(name = "order_user", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User userInfo;
    private Date date;
    private String name;
    private String lastName;
    private String email;
    private String address;
    @ManyToMany
    @JoinTable(name = "order_product", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();


}
