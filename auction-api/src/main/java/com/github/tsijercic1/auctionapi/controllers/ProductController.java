package com.github.tsijercic1.auctionapi.controllers;

import com.github.tsijercic1.auctionapi.configuration.FileStorageConfiguration;
import com.github.tsijercic1.auctionapi.exceptions.ResourceNotFoundException;
import com.github.tsijercic1.auctionapi.exceptions.UnauthorizedException;
import com.github.tsijercic1.auctionapi.models.Product;
import com.github.tsijercic1.auctionapi.models.ProductPicture;
import com.github.tsijercic1.auctionapi.models.User;
import com.github.tsijercic1.auctionapi.request.ProductRequest;
import com.github.tsijercic1.auctionapi.repositories.UserRepository;
import com.github.tsijercic1.auctionapi.security.Authorizer;
import com.github.tsijercic1.auctionapi.security.CurrentUser;
import com.github.tsijercic1.auctionapi.security.UserPrincipal;
import com.github.tsijercic1.auctionapi.services.CategoryService;
import com.github.tsijercic1.auctionapi.services.FileStorageService;
import com.github.tsijercic1.auctionapi.services.ProductPictureService;
import com.github.tsijercic1.auctionapi.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    final ProductService productService;
    final UserRepository userRepository;
    final FileStorageService fileStorageService;
    final CategoryService categoryService;
    final FileStorageConfiguration fileStorageConfiguration;
    final ProductPictureService productPictureService;

    public ProductController(ProductService productService, UserRepository userRepository, FileStorageService fileStorageService, CategoryService categoryService, FileStorageConfiguration fileStorageConfiguration, ProductPictureService productPictureService) {
        this.productService = productService;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
        this.categoryService = categoryService;
        this.fileStorageConfiguration = fileStorageConfiguration;
        this.productPictureService = productPictureService;
    }


    @GetMapping("/products")
    @PermitAll
    public ResponseEntity<Iterable<Product>> getAll(Pageable pageable) {
        return ResponseEntity.ok(productService.getAll(pageable));
    }

    @GetMapping("/{userId}/products")
    public ResponseEntity<List<Product>> getAllForUser(@PathVariable Long userId, @CurrentUser UserPrincipal userPrincipal) {
        Authorizer.validateAuthority(userPrincipal,"USER");
        User user = checkAuthorization(userId, userPrincipal);
        return ResponseEntity.ok(productService.getAllForUser(user));
    }

    @PostMapping("/{userId}/products")
    public ResponseEntity<Product> create(@PathVariable Long userId, @Valid @RequestBody ProductRequest productRequest, @CurrentUser UserPrincipal userPrincipal) {
        Authorizer.validateAuthority(userPrincipal,"USER");
        User user = checkAuthorization(userId, userPrincipal);
        Product product = new Product();
        transformToProduct(productRequest, user, product);
        return ResponseEntity.ok(productService.create(product));
    }

    @PostMapping("/{userId}/products/{id}/images")
    public ResponseEntity<Iterable<ProductPicture>> uploadPictures(@PathVariable("userId") Long userId,
                                                                   @PathVariable("id") Long id,
                                                                   @CurrentUser UserPrincipal userPrincipal,
                                                                   @RequestParam("pictures") MultipartFile[] multipartFiles) {
        Authorizer.validateAuthority(userPrincipal,"USER");
        User user = checkAuthorization(userId, userPrincipal);

        Product product = productService.get(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        if (!product.getSeller().getId().equals(userId)) {
            throw new UnauthorizedException("You do not own this product!");
        }
        return ResponseEntity.ok(Arrays.stream(multipartFiles).map(multipartFile ->
                productPictureService.create(multipartFile, product)
        ).collect(Collectors.toList()));
    }

    private User checkAuthorization(@PathVariable Long userId, @CurrentUser UserPrincipal userPrincipal) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("User", "Id", userId);
        }
        if (!user.get().getEmail().equals(userPrincipal.getEmail())) {
            throw new UnauthorizedException("You are not logged into your account");
        }
        return user.get();
    }

    private void transformToProduct(@RequestBody @Valid ProductRequest productRequest, User user, Product product) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setStartPrice(productRequest.getStartPrice());
        product.setSubcategory(
                categoryService
                        .getSubcategoryByName(productRequest.getSubcategory())
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Subcategory", "name", productRequest.getSubcategory())));
        product.setSeller(user);
        product.setAuctionStart(productRequest.getAuctionStart());
        product.setAuctionEnd(productRequest.getAuctionEnd());
    }
}
