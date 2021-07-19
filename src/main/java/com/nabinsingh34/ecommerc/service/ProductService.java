package com.nabinsingh34.ecommerc.service;

import com.nabinsingh34.ecommerc.dto.ProductRequest;
import com.nabinsingh34.ecommerc.model.Product;
import com.nabinsingh34.ecommerc.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    private final String UPLOAD_PATH="E:\\project\\java\\ecommerc\\src\\main\\resources\\images";
    public void createProduct(ProductRequest productRequest) throws IOException {
        Product product= new Product();
        product.setName(productRequest.getName());
        product.setUser(productRequest.getUser());
        product.setImage(uploadImage(productRequest.getFile()));
        productRepository.save(product);
    }

    private String uploadImage(MultipartFile file) throws IOException {
        Files.copy(file.getInputStream(), Paths.get(UPLOAD_PATH+ File.separator+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        return UPLOAD_PATH+ File.separator+file.getOriginalFilename();
    }
}
