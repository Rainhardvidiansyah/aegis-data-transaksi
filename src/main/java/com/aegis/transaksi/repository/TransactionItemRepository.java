package com.aegis.transaksi.repository;

import com.aegis.transaksi.entity.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItem, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM TransactionItem WHERE" +
            "transaction_item_id = :transaction_item_id" +
            "AND transaction_id = :transaction_id")
    Optional<TransactionItem> findTransactionItemByTransactionId(@Param("transaction_item_id") UUID transactionItemId, @Param("transaction_id") UUID transaction_id);

    /*
    @Modifying
@Query(nativeQuery = true, value = "UPDATE TransactionItem SET quantity = quantity + :quantity, " +
        "total_price = (price * (quantity + :quantity)) " +
        "WHERE transaction_item_id = :transaction_item_id AND transaction_id = :transaction_id")
int updateTransactionItemQuantity(@Param("transaction_item_id") UUID transactionItemId,
                                   @Param("transaction_id") UUID transactionId,
                                   @Param("quantity") int quantity);

     */
}
