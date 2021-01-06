package com.dawid.converters;

import com.dawid.commands.CategoryCommand;
import com.dawid.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Nullable
    @Synchronized
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }
        CategoryCommand command = new CategoryCommand();
        command.setId(source.getId());
        command.setDescription(source.getDescription());

        return command;

    }
}
