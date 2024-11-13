package com.aegis.transaksi.controller;


import com.aegis.transaksi.dto.TransactionRequest;
import com.aegis.transaksi.entity.Transaction;
import com.aegis.transaksi.entity.TransactionItem;
import com.aegis.transaksi.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/aegis/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CASHIER')")
    public ResponseEntity<Transaction> saveTransaction(@RequestBody TransactionRequest request) {

        return null;

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> getTransactionById(@PathVariable UUID id){

        //var transaction =  this.transactionService.getTransactionById(id);

        //return new ResponseEntity<>(transaction, HttpStatus.OK);
        return null;
    }


}
