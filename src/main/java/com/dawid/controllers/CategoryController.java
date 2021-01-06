package com.dawid.controllers;


import com.dawid.commands.CategoryCommand;
import com.dawid.domain.Category;
import com.dawid.domain.Product;
import com.dawid.services.CategoryService;
import com.dawid.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Set;

@Controller
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }


    @GetMapping("/admin/newcategory")
    public String showAddCategory(Model model){
        model.addAttribute("category", new CategoryCommand());

        return "category/add";
    }


    @PostMapping("/admin/newcategory")
    public String addCategory(@ModelAttribute("category") @Valid CategoryCommand categoryCommand, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return "category/add";
        }

        categoryService.saveNewCategory(categoryCommand);

        return "redirect:/admin/showcategories";

    }

    @GetMapping("/admin/showcategories")
    public String showCategories(Model model){
        model.addAttribute("categories", categoryService.getCategories());

        return "category/showall";
    }

    @GetMapping("/admin/category/{id}/delete")
    public String deleteCategory(@PathVariable String id){
        Category category = categoryService.getCategoryById(Long.valueOf(id));
        Set<Product> productSet = productService.getAllByCategory(category);

        for(Product product : productSet){
            product.getCategories().remove(category);
            productService.saveProduct(product);
        }

        categoryService.deleteCategoryById(Long.valueOf(id));

        return "redirect:/admin/showcategories";
    }


}
