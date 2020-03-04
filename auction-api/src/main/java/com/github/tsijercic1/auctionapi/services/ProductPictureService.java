package com.github.tsijercic1.auctionapi.services;

import com.github.tsijercic1.auctionapi.models.Product;
import com.github.tsijercic1.auctionapi.models.ProductPicture;
import com.github.tsijercic1.auctionapi.repositories.ProductPictureRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductPictureService {
    final ProductPictureRepository productPictureRepository;
    final FileStorageService fileStorageService;

    public ProductPictureService(ProductPictureRepository productPictureRepository, FileStorageService fileStorageService) {
        this.productPictureRepository = productPictureRepository;
        this.fileStorageService = fileStorageService;
    }

    public ProductPicture create(MultipartFile multiPartFile, Product product) {
        String url = fileStorageService.storeFile(multiPartFile, product.getId(), product.getSeller().getId());
        ProductPicture productPicture = new ProductPicture();
        productPicture.setUrl(url);
        productPicture.setProduct(product);
        return productPictureRepository.save(productPicture);
    }
}
