package com.aegis.transaksi.service;

import com.aegis.transaksi.entity.Products;
import com.aegis.transaksi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Products> getAllProducts() {
        return this.productRepository.findAll();
    }
}
