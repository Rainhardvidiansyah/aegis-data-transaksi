package com.aegis.transaksi.repository;

import com.aegis.transaksi.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Products, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM products p WHERE p.product_name = :product_name")
    Products findProductByProductName(@Param("product_name") String productName);



}
