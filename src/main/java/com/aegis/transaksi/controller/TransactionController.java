package com.aegis.transaksi.controller;


import com.aegis.transaksi.dto.TransactionRequestDto;
import com.aegis.transaksi.dto.responses.TransactionResponseDto;

import com.aegis.transaksi.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
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


    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CASHIER')")
    public ResponseEntity<?> saveTransaction(@RequestBody @Valid List<TransactionRequestDto> request, BindingResult result) {

        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        var transaction = transactionService.createTransaction(request);

        var response = TransactionResponseDto.response(transaction);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }











    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> getTransactionById(@PathVariable UUID id){
        //implement service and create a response class for this!!!
        return null;
    }


}
