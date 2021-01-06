package com.dawid.converters;

import com.dawid.commands.OrderCommand;
import com.dawid.domain.Order;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class OrderToOrderCommand implements Converter<Order, OrderCommand> {

    private final ProductToProductCommand productToProductCommand;


    public OrderToOrderCommand(ProductToProductCommand productToProductCommand) {
        this.productToProductCommand = productToProductCommand;
    }

    @Nullable
    @Synchronized
    @Override
    public OrderCommand convert(Order source) {
        if(source == null){
            return null;
        }


        OrderCommand command = new OrderCommand();
        command.setId(source.getId());
        command.setDate(source.getDate());
        command.setPrice(source.getPrice());
        command.setUserInfo(source.getUserInfo());
        command.setAddress(source.getAddress());
        command.setEmail(source.getEmail());
        command.setName(source.getName());
        command.setLastName(source.getLastName());

        source.getProducts().forEach(product -> command.getProducts().add(productToProductCommand.convert(product)));


        return command;
    }
}
