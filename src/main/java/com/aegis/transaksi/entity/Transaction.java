package com.aegis.transaksi.entity;

import com.aegis.transaksi.auditor.BaseEntity;
import com.aegis.transaksi.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "transactions") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString
public class Transaction extends BaseEntity {

    /*
    class transaction:
    uuid -> id
    users -> users
    transaction_date -> transactionDate
    Bigdecimal -> totalAmount
    List<TransactionItem> transactionItem

     */


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id")
    private UUID transactionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private Users user;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "total_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private TransactionStatus status;


    @JsonBackReference
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TransactionItem> transactionItem;

    public void addTransactionItem(List<TransactionItem> items){
        this.transactionItem = items;
        items.forEach(a -> a.setTransaction(this));
    }


    public void removeTransactionItem(){
        this.transactionItem.clear();
       // transactionItem.setTransaction(null);
    }

    public void addAllItem(List<TransactionItem> items){
        this.transactionItem.addAll(items);
    }

    // Not-null property references a transient value - transient instance must be saved before current operation


    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public List<TransactionItem> getTransactionItem() {
        return transactionItem;
    }

    public void setTransactionItem(List<TransactionItem> transactionItem) {
        this.transactionItem = transactionItem;
    }
}
