package com.aegis.transaksi.dto.responses;

import com.aegis.transaksi.entity.Transaction;
import com.aegis.transaksi.entity.TransactionItem;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class TransactionResponseDto {

    private UUID transaction_id;

    private List<UUID> transactionItemsId;

    private List<BigDecimal> totalPrice;



    public static TransactionResponseDto response(Transaction transaction){

        List<UUID> transactionItemId = transaction.getTransactionItem().stream()
                .map(TransactionItem::getTransactionItemId).collect(Collectors.toList());
        List<BigDecimal> totalPrice = transaction.getTransactionItem().stream()
                .map(TransactionItem::getTotalPrice).collect(Collectors.toList());

        TransactionResponseDto response = new TransactionResponseDto();
        response.setTransaction_id(transaction.getTransactionId());
        response.setTransactionItemsId(transactionItemId);
        response.setTotalPrice(totalPrice);



        return response;
    }
}
