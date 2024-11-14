package com.aegis.transaksi.dto;


import jakarta.validation.constraints.Min;
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

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be greater than or equal to 1")
    private Integer quantity;

    public TransactionRequestDto(UUID productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }


}
