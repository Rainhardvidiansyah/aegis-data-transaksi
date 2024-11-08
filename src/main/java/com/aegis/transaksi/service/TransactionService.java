package com.aegis.transaksi.service;

import com.aegis.transaksi.entity.Products;
import com.aegis.transaksi.entity.Transaction;
import com.aegis.transaksi.entity.TransactionItem;
import com.aegis.transaksi.repository.ProductRepository;
import com.aegis.transaksi.repository.TransactionItemRepository;
import com.aegis.transaksi.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class TransactionService {

        // menciptakan layer utama untuk laporan.
        // Jadi transaksi ini adalah kumpulan dari data yang ada detail transaksi
        // dalam detail transaksi ada product dan kuantitas product yang dibeli



        private final TransactionRepository transactionRepository;
        private final ProductRepository productRepository;
        private final TransactionItemRepository transactionItemRepository;

        public TransactionService(TransactionRepository transactionRepository, ProductRepository productRepository, TransactionItemRepository transactionItemRepository) {
            this.transactionRepository = transactionRepository;
            this.productRepository = productRepository;
            this.transactionItemRepository = transactionItemRepository;
        }

    @Transactional
    public Transaction saveTransaction(Transaction transaction, List<TransactionItem> transactionItems) {


        BigDecimal totalAmount = BigDecimal.ZERO;

        for (TransactionItem item : transactionItems) {

            item.setTransaction(transaction);

            BigDecimal kuantitas = BigDecimal.valueOf(item.getQuantity());

            BigDecimal totalBelanja = item.getPrice().multiply(kuantitas);
            item.setTotalPrice(totalBelanja);

            totalAmount = totalAmount.add(totalBelanja);

            Products product = item.getProduct();

            int updatedStock = product.getStocks() - item.getQuantity();

            if (updatedStock < 0) {
                throw new IllegalArgumentException("Stok untuk produk " + product.getProductName() + " tidak cukup.");
            }
            product.setStocks(updatedStock);
            productRepository.save(product);
            transactionItemRepository.save(item);
        }

        transaction.setTransactionItem(transactionItems);
        transaction.setTotalAmount(totalAmount);

        return transactionRepository.save(transaction);
    }



    public Optional<Transaction> getTransactionById(UUID id){
            return this.transactionRepository.findById(id);
    }


}


