package com.aegis.transaksi.controller;

import com.aegis.transaksi.dto.TransactionRequestDto;
import com.aegis.transaksi.service.TransactionServiceFixing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/aegis/transaction")
public class TransactionControllerFixing {

    private final TransactionServiceFixing transactionService;

    @Autowired
    public TransactionControllerFixing(TransactionServiceFixing transactionService){
        this.transactionService = transactionService;
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CASHIER')")
    @PostMapping("/")
    public ResponseEntity<?> createTransaction(@RequestBody List<TransactionRequestDto> requestDtoList){

        var transaction = transactionService.createTransaction(requestDtoList);

        return new ResponseEntity<>("Transaction id: " + transaction.getTransactionId(),
                HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequestDto requestDtoList){

        transactionService.addTransaction(requestDtoList.getProductId(), requestDtoList.getQuantity());

        return new ResponseEntity<>("Transaction just created", HttpStatus.CREATED);
    }

















}
