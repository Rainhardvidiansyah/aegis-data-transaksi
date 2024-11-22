package com.aegis.transaksi.controller;


import com.aegis.transaksi.dto.TransactionRequestDto;
import com.aegis.transaksi.dto.responses.TransactionResponseDto;

import com.aegis.transaksi.entity.Transaction;
import com.aegis.transaksi.service.TransactionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/aegis/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

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


    @PutMapping("/add/{transactionId}/{itemId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CASHIER')") // add/3e2b54fd-46fa-4bf7-a4b6-ef1de30b705a/8b15c3dd-6e78-4d4c-a915-ca00f8519778?quantity=2
    public ResponseEntity<?> addProductToTransaction(@PathVariable("transactionId") String transactionId,
                                                     @PathVariable("itemId") String itemId, @RequestParam int quantity){

        logger.info("addProductToTransaction get hit");

        var transaction = transactionService.addProductAndUpdateTotalPrice(transactionId, itemId, quantity);
        var response = TransactionResponseDto.response(transaction);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> getAllTransactionData(){

        List<TransactionResponseDto> listResponse = new ArrayList<>();

        var transactions = this.transactionService.getAllTransactions();

        for(Transaction transaction: transactions){
            listResponse.add(TransactionResponseDto.response(transaction));
        }


        return new ResponseEntity<>(listResponse, HttpStatus.OK);
    }




    @GetMapping("/{transactionId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> getTransactionById(@PathVariable("transactionId") String transactionId){

        var transaction = this.transactionService.getTransactionById(transactionId);

        var response = TransactionResponseDto.response(transaction);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
