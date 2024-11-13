package com.aegis.transaksi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;


@NoArgsConstructor
@Getter @Setter @ToString
public class TransactionRequestDto {


    private UUID productId;

    private int quantity;

    public TransactionRequestDto(UUID productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }


}
