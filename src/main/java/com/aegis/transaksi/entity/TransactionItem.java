package com.aegis.transaksi.entity;

import com.aegis.transaksi.auditor.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "transaction_item")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
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

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "price", precision = 15, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "total_price", precision = 15, scale = 2, nullable = false)
    private BigDecimal totalPrice;
}
