package com.github.tsijercic1.auctionapi.services;

import com.github.tsijercic1.auctionapi.exceptions.ResourceNotFoundException;
import com.github.tsijercic1.auctionapi.models.Category;
import com.github.tsijercic1.auctionapi.models.Product;
import com.github.tsijercic1.auctionapi.models.Subcategory;
import com.github.tsijercic1.auctionapi.models.User;
import com.github.tsijercic1.auctionapi.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public Product get(Long id) {
        return productRepository.getOne(id);
    }


    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product product) {
        if (get(id) == null) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        product.setId(id);
        return productRepository.save(product);
    }

    public Iterable<Product> getAll(String categoryName, String subcategoryName) {
        if (subcategoryName != null && categoryName != null) {
            List<Category> categories = categoryService
                    .getCategories()
                    .stream()
                    .filter(category ->
                            category
                                    .getName()
                                    .equals(categoryName))
                    .collect(Collectors.toList());
            if (categories.size() != 0) {
                List<Subcategory> subcategories = categories
                        .get(0)
                        .getSubcategories()
                        .stream()
                        .filter(subcategory -> subcategory.getName().equals(subcategoryName))
                        .collect(Collectors.toList());
                if (subcategories.size() != 0) {
                    return productRepository.findAllBySubcategory(subcategories.get(0));
                }
            }
            return new ArrayList<>();
        } else if (categoryName != null) {
            return productRepository
                    .findAll()
                    .stream()
                    .filter(product -> product.getSubcategory().getCategory().getName().equals(categoryName))
                    .collect(Collectors.toList());
        }
        return productRepository.findAll();
    }

    public List<Product> getAllForUser(User user) {
        return productRepository.findAll().stream().filter(product ->
            product.getSeller().getId().equals(user.getId())
        ).collect(Collectors.toList());
    }
}
