package com.aegis.transaksi.service;


import com.aegis.transaksi.entity.TransactionItem;
import com.aegis.transaksi.repository.TransactionItemRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionItemService {

    private final TransactionItemRepository transactionItemRepository;

    public TransactionItemService(TransactionItemRepository transactionItemRepository) {
        this.transactionItemRepository = transactionItemRepository;
    }

    public TransactionItem saveTransactionItem(TransactionItem item){
        return this.transactionItemRepository.save(item);
    }

}
