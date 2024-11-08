package com.aegis.transaksi.entity;

import com.aegis.transaksi.auditor.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "transaction_item")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TransactionItem extends BaseEntity {

    /*
    class: transactionitem

    UUID transactionItemId
    Transaction transaction
    Products product
     Integer quantity
     BigDecimal price
     BigDecimal totalPrice
     */

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
