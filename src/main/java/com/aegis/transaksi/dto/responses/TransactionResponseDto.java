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

    private BigDecimal totalAmount;

    //TODO: use Collectors.teeing() instead of using double stream like below. Double stream will make your app inefficient


    public static TransactionResponseDto response(Transaction transaction){

        List<TransactionItem> transactionItems = transaction.getTransactionItem();


        Tuple result = transactionItems.stream().collect(
                        Collectors.teeing(
                                Collectors.mapping(TransactionItem::getTransactionItemId, Collectors.toList()),
                                Collectors.mapping(TransactionItem::getTotalPrice, Collectors.toList()),
                                (transactionItemsId, totalPrice) -> new Tuple(transactionItemsId, totalPrice)
                        )
                );

        TransactionResponseDto response = new TransactionResponseDto();
        response.setTransaction_id(transaction.getTransactionId());
        response.setTransactionItemsId(result.transactionItemIds);
        response.setTotalPrice(result.totalPrices);
        response.setTotalAmount(transaction.getTotalAmount());

        return response;
    }
}


