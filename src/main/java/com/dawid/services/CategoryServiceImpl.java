package com.dawid.services;

import com.dawid.commands.CategoryCommand;
import com.dawid.converters.CategoryCommandToCategory;
import com.dawid.converters.CategoryToCategoryCommand;
import com.dawid.domain.Category;
import com.dawid.exceptions.NotFoundException;
import com.dawid.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryCommandToCategory categoryCommandToCategory;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryCommandToCategory categoryCommandToCategory, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryRepository = categoryRepository;
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }



    @Override
    public Category getCategoryById(Long l) {
        Optional<Category> productOptional = categoryRepository.findById(l);
        if(!productOptional.isPresent()){
            throw new NotFoundException("Nie znaleziono");
        }
        return categoryRepository.findById(l).get();
    }

    @Override
    public Set<Category> getCategories() {
        Set<Category> categories = new TreeSet<>(Comparator.comparing(Category::getId));
        categoryRepository.findAll().iterator().forEachRemaining(categories::add);

        return categories;
    }

    @Override
    public Set<CategoryCommand> getCategoryCommands() {
        Set<CategoryCommand> categories = new TreeSet<>(Comparator.comparing(CategoryCommand::getId));
        categoryRepository.findAll().stream().map(category -> categoryToCategoryCommand.convert(category)).iterator().forEachRemaining(categories::add);

        return categories;
    }

    @Override
    public void saveNewCategory(CategoryCommand categoryCommand) {
        Category category = categoryCommandToCategory.convert(categoryCommand);

        categoryRepository.save(category);

    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }



}
