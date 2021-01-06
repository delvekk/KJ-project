package com.dawid.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Review {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String review;

    private Long productId;

    @ManyToOne
    @JoinTable(name = "review_user", joinColumns = @JoinColumn(name = "review_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;

    @ManyToOne
    @JoinTable(name = "review_product", joinColumns = @JoinColumn(name = "review_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Product product;

    @Temporal(TemporalType.DATE)
    private Date date;


}
