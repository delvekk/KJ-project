package com.dawid.services;

import com.dawid.commands.CategoryCommand;
import com.dawid.converters.CategoryCommandToCategory;
import com.dawid.converters.CategoryToCategoryCommand;
import com.dawid.domain.Category;
import com.dawid.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CategoryCommandToCategory categoryCommandToCategory;

    @Mock
    CategoryToCategoryCommand categoryToCategoryCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(categoryRepository, categoryCommandToCategory, categoryToCategoryCommand);
    }

    @Test
    public void getCategoryById() {
        Category category = new Category();
        category.setId(1L);
        category.setProducts(new HashSet<>());
        category.setDescription("DESC");


        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));


        Category category2 = categoryService.getCategoryById(anyLong());

        assertEquals(category.getId(), category2.getId());
        assertEquals(category.getDescription(), category.getDescription());
        assertEquals(category.getProducts().size(), category2.getProducts().size());
        verify(categoryRepository, times(2)).findById(anyLong());
    }

    @Test
    public void getCategories() {
        List<Category> categories = new ArrayList<>();

        Category cat1 = new Category();
        cat1.setId(1L);
        categories.add(cat1);

        Category cat2 = new Category();
        cat2.setId(2L);
        categories.add(cat2);

        when(categoryRepository.findAll()).thenReturn(categories);

        Set<Category> categoryList = categoryService.getCategories();

        assertEquals(2, categoryList.size());
        verify(categoryRepository, times(1)).findAll();
    }


    @Test
    public void getCategoryCommands(){
        List<Category> categories = new ArrayList<>();

        Category cat1 = new Category();
        cat1.setId(1L);
        categories.add(cat1);

        Category cat2 = new Category();
        cat2.setId(2L);
        categories.add(cat2);

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(1L);

        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(2L);

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryToCategoryCommand.convert(cat1)).thenReturn(categoryCommand1);
        when(categoryToCategoryCommand.convert(cat2)).thenReturn(categoryCommand2);

        Set<CategoryCommand> categoryCommands = categoryService.getCategoryCommands();

        assertEquals(2, categoryCommands.size());
        verify(categoryRepository, times(1)).findAll();

    }

    @Test
    public void saveNewCategory(){
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(1L);

        Category category = new Category();
        category.setId(1L);

        when(categoryCommandToCategory.convert(any())).thenReturn(category);

        categoryService.saveNewCategory(categoryCommand);

        verify(categoryRepository, times(1)).save(any());
    }



    @Test
    public void deleteCategoryById(){
        Category category = new Category();
        category.setId(1L);
        category.setProducts(new HashSet<>());
        category.setDescription("DESC");


        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        categoryService.deleteCategoryById(1L);

        verify(categoryRepository, times(1)).delete(any());
    }

}