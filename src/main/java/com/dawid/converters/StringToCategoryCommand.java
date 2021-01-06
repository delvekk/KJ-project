package com.dawid.converters;

import com.dawid.commands.CategoryCommand;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoryCommand implements Converter<String, CategoryCommand> {


    @Override
    public CategoryCommand convert(String s) {
        CategoryCommand command = new CategoryCommand();
        command.setId(Long.valueOf(s));

        return command;
    }
}
