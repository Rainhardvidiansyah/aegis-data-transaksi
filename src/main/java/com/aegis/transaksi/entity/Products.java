package com.aegis.transaksi.entity;

import com.aegis.transaksi.auditor.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "products")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Products extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price", precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "stocks")
    private int stocks;




}
