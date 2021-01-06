package com.dawid.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;
    private Integer amount;
    private Boolean discount;
    private Boolean recent;
    @Lob
    private String description;
    private String image_url;
    @ManyToMany
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Review> comments = new HashSet<>();
    @ManyToMany(mappedBy = "products")
    private Set<Order> orders = new HashSet<>();


}
