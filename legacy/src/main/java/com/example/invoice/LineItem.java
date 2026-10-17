package com.example.invoice;

import java.math.BigDecimal;

/** Legacy (Java 8) mutable-style POJO. */
public class LineItem {
    private final String product;
    private final int quantity;
    private final BigDecimal unitPrice;

    public LineItem(String product, int quantity, BigDecimal unitPrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal lineTotal() {
        return unitPrice.multiply(new BigDecimal(quantity));
    }
}
