package com.dawid.services;

import com.dawid.commands.CategoryCommand;
import com.dawid.domain.Category;

import java.util.Set;

public interface CategoryService {


    Category getCategoryById(Long l);

    Set<Category> getCategories();

    Set<CategoryCommand> getCategoryCommands();

    void saveNewCategory(CategoryCommand categoryCommand);

    void deleteCategoryById(Long id);


}
