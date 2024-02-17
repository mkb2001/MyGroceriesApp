package com.example.groceries.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        try {
            Category savedCategory = categoryService.addCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PutMapping(path = "/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable("categoryId") @NonNull UUID categoryId,
            @RequestBody CategoryRequest name) {
        try {
            String updatedCategory = categoryService.updateCategory(categoryId, name);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedCategory);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") @NonNull UUID categoryId) {
        try {
            String deletedCategory = categoryService.deleteCategory(categoryId);
            return ResponseEntity.status(HttpStatus.CREATED).body(deletedCategory);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
