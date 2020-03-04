package com.github.tsijercic1.auctionapi.services;

import com.github.tsijercic1.auctionapi.models.Category;
import com.github.tsijercic1.auctionapi.models.Subcategory;
import com.github.tsijercic1.auctionapi.repositories.CategoryRepository;
import com.github.tsijercic1.auctionapi.repositories.SubcategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    final SubcategoryRepository subcategoryRepository;
    final CategoryRepository categoryRepository;

    public CategoryService(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    public Optional<Subcategory> getSubcategoryByName(String name) {
        return subcategoryRepository.findByName(name);
    }

    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
