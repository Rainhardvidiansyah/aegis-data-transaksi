package com.aegis.transaksi.dto.responses;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Tuple{
public List<UUID> transactionItemIds;
public List<BigDecimal> totalPrices;

public Tuple(List<UUID> transactionItemIds, List<BigDecimal> totalPrices) {
    this.transactionItemIds = transactionItemIds;
    this.totalPrices = totalPrices;
}

    public List<BigDecimal> getTotalPrices() {
        return totalPrices;
    }

    public void setTotalPrices(List<BigDecimal> totalPrices) {
        this.totalPrices = totalPrices;
    }

    public List<UUID> getTransactionItemIds() {
        return transactionItemIds;
    }

    public void setTransactionItemIds(List<UUID> transactionItemIds) {
        this.transactionItemIds = transactionItemIds;
    }
}
