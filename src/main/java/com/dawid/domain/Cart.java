package com.dawid.domain;

import com.dawid.commands.ProductCommand;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {


    private Integer price;
    private Map<ProductCommand, Integer> products = new HashMap<>();


    public Integer getPrice() {
        this.price = 0;
        for (ProductCommand product : products.keySet()) {
            this.price += product.getPrice() * products.get(product);
        }
        return price;
    }

    public Integer countProducts(){
        Integer numberOfProducts = 0;
        for(Integer amount : products.values()){
            numberOfProducts+= amount;
        }

        return numberOfProducts;
    }

}
