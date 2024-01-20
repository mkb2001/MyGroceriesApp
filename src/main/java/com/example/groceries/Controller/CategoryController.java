package com.example.groceries.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.groceries.Entity.Category;
import com.example.groceries.Request.CategoryRequest;
import com.example.groceries.Service.CategoryService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;

    @PostMapping(path = "/add")
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return ResponseEntity.ok("Category added successfully");
    }

    @GetMapping
    public List<Category> getCategories(){
        return categoryService.getCategories();
    }

    @PutMapping(path = "/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable("categoryId") UUID categoryId,
        @RequestBody CategoryRequest name){
            categoryService.updateCategory(categoryId, name);
            return ResponseEntity.ok("Updated category");
        }

    @DeleteMapping(path = "/{categoryId}") 
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") UUID categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
