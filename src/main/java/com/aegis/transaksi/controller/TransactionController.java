package com.aegis.transaksi.controller;


import com.aegis.transaksi.dto.TransactionRequest;
import com.aegis.transaksi.dto.TransactionRequestDto;
import com.aegis.transaksi.dto.responses.TransactionResponseDto;
import com.aegis.transaksi.entity.Transaction;

import com.aegis.transaksi.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<?> saveTransaction(@RequestBody @Valid TransactionRequestDto request, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        var transactionRequest = new TransactionRequestDto(request.getProductId(), request.getQuantity());

        List<TransactionRequestDto> transactionRequestList = new ArrayList<>();

        transactionRequestList.add(transactionRequest);

        var transaction = transactionService.createTransaction(transactionRequestList);

        var response = TransactionResponseDto.response(transaction);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> getTransactionById(@PathVariable UUID id){

        //var transaction =  this.transactionService.getTransactionById(id);

        //return new ResponseEntity<>(transaction, HttpStatus.OK);
        return null;
    }


}
