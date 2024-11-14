package com.aegis.transaksi.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;


@NoArgsConstructor
@Getter @Setter @ToString
public class TransactionRequestDto {


    @NotNull(message = "Product Id tidak boleh null")
    private UUID productId;

    @Size(min = 1, message = "Kuantitas tidak boleh kurang dari 1")
    @NotNull(message = "Null tidak boleh digunakan di sini")
    private int quantity;

    public TransactionRequestDto(UUID productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }


}
