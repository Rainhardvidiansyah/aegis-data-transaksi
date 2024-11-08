package com.aegis.transaksi.dto;

import com.aegis.transaksi.entity.Transaction;
import com.aegis.transaksi.entity.TransactionItem;
import com.aegis.transaksi.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TransactionRequest {


        private Transaction transaction;
        private List<TransactionItem> transactionItems;
        private Users users;
        private LocalDateTime transactionDate;


}
