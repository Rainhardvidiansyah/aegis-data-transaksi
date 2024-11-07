package com.aegis.transaksi.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "transaction_item")
public class TransactionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_item_id")
    private UUID transactionItemId;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @Column(nullable = false)
    private Integer quantity; //Jumlah product

    @Column(name = "price", precision = 15, scale = 2, nullable = false)
    private BigDecimal price;


    @Column(name = "total_price", precision = 15, scale = 2, nullable = false)
    private BigDecimal totalPrice;
}
