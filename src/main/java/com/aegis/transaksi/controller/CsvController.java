package com.aegis.transaksi.controller;


import com.aegis.transaksi.entity.Transaction;
import com.aegis.transaksi.service.CsvReportService;
import com.aegis.transaksi.service.TransactionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/aegis")
public class CsvController {

    private final TransactionService transactionService;
    private final CsvReportService csvReportService;

    public CsvController(TransactionService transactionService, CsvReportService csvReportService) {
        this.transactionService = transactionService;
        this.csvReportService = csvReportService;
    }


    @GetMapping("/transaction/{id}/csv") //transaction/agj94gjt944/csv
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CASHIER')")
    public ResponseEntity<byte[]> createReport(@PathVariable UUID id) {
        try {

            Optional<Transaction> transaction = transactionService.getTransactionById(id);

            File csvFile = csvReportService.generateTransactionCsvFile(transaction.get());

            byte[] csvContent;
            try (FileInputStream fis = new FileInputStream(csvFile)) {
                csvContent = fis.readAllBytes();
            }
            csvFile.delete();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transaction_report.csv")
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(csvContent);

        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }


    //TINGGAL IMPLEMENTASI CETAK DARI TANGGAL BERAPA KE TANGGAL BERAPA
    //TIMES UP!!




}
