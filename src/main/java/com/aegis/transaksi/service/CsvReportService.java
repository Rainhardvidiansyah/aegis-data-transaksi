package com.aegis.transaksi.service;

import com.aegis.transaksi.entity.Transaction;
import com.aegis.transaksi.entity.TransactionItem;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import com.opencsv.CSVWriter;
import java.io.IOException;

@Service
public class CsvReportService {

    public File generateTransactionCsvFile(Transaction transaction) throws IOException, IOException {
        File csvFile = File.createTempFile("transaction_report_", ".csv");

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {


            writer.writeNext(new String[]{"Product Name", "Quantity", "Price", "Total Price"});

            for (TransactionItem item : transaction.getTransactionItem()) {
                writer.writeNext(new String[]{
                        item.getProduct().getProductName(),
                        String.valueOf(item.getQuantity()),
                        item.getPrice().toString(),
                        item.getTotalPrice().toString()
                });
            }

            writer.writeNext(new String[]{"", "", "Total Amount", transaction.getTotalAmount().toString()});
        }

        return csvFile;
    }

}
