package com.example.groceries.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.groceries.Entity.Category;
import com.example.groceries.Request.CategoryRequest;
import com.example.groceries.repository.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {
    Logger logger = LoggerFactory.getLogger(CategoryService.class);

	private final CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public void addCategory(Category category){
		logger.info("add category method in Category Service has started");
		Optional<Category> categoryByName = categoryRepository.findCategoryByName(category.getName());
		if (categoryByName.isPresent()) {
			throw new IllegalStateException("Category already exists");
		}
		
		categoryRepository.save(category);
		logger.info(category + " created");
		logger.info("add category method in Category Service has ended");
	}

    public List<Category> getCategories(){
		logger.info("get category method in Category Service has started");
		return categoryRepository.findAll();
	}

	public void deleteCategory(UUID categoryId) {
		logger.info("delete category method in Category Service has started");
		boolean idExists = categoryRepository.existsById(categoryId);
		if (!idExists) {
			throw new IllegalStateException("Category with id " + categoryId + " does not exist!");
		}
		categoryRepository.deleteById(categoryId);
		logger.info("delete category method in Category Service has ended");
	}

	@Transactional
    public void updateCategory(UUID categoryId, CategoryRequest category){
		logger.info("update category method in Category Service has started");
		Category dbCategory = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new IllegalStateException(
				"Category with id " + categoryId + " does not exist!"));

		if(category.name() != null && category.name().length() > 0 && !Objects.equals(dbCategory.getName(), category.name())) {
			dbCategory.setName(category.name());
		}
		logger.info("update category method in Category Service has ended");	
    }

}
