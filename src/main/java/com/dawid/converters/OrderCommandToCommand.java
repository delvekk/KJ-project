package com.dawid.converters;

import com.dawid.commands.OrderCommand;
import com.dawid.domain.Order;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class OrderCommandToCommand implements Converter<OrderCommand, Order> {

    private final ProductCommandToProduct productCommandToProduct;

    public OrderCommandToCommand(ProductCommandToProduct productCommandToProduct) {
        this.productCommandToProduct = productCommandToProduct;
    }

    @Nullable
    @Synchronized
    @Override
    public Order convert(OrderCommand source) {
        if(source == null){
            return null;
        }

        Order order = new Order();
        order.setId(source.getId());
        order.setDate(source.getDate());
        order.setPrice(source.getPrice());
        order.setUserInfo(source.getUserInfo());
        order.setAddress(source.getAddress());
        order.setEmail(source.getEmail());
        order.setName(source.getName());
        order.setLastName(source.getLastName());

        source.getProducts().forEach(productCommand -> order.getProducts().add(productCommandToProduct.convert(productCommand)));

        return order;
    }

}
