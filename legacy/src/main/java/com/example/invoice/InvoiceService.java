package com.example.invoice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Legacy (Java 8) implementation. */
public class InvoiceService {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.18");

    public BigDecimal subtotal(List<LineItem> items) {
        BigDecimal sum = BigDecimal.ZERO;
        for (LineItem item : items) {
            sum = sum.add(item.lineTotal());
        }
        return sum;
    }

    public BigDecimal applyDiscount(BigDecimal amount, int percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("percent must be between 0 and 100");
        }
        BigDecimal factor = new BigDecimal(100 - percent).divide(new BigDecimal(100));
        return amount.multiply(factor).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal tax(BigDecimal amount) {
        return amount.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
    }

    public String customerDisplayName(String firstName, String lastName) {
        // Legacy bug: does not handle a missing first name.
        return lastName.toUpperCase() + ", " + firstName;
    }

    public List<String> topProducts(List<LineItem> items, int n) {
        List<LineItem> sorted = new ArrayList<LineItem>(items);
        Collections.sort(sorted, new Comparator<LineItem>() {
            @Override
            public int compare(LineItem a, LineItem b) {
                return b.lineTotal().compareTo(a.lineTotal());
            }
        });
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < Math.min(n, sorted.size()); i++) {
            result.add(sorted.get(i).getProduct());
        }
        return result;
    }

    public Map<String, Integer> quantityByProduct(List<LineItem> items) {
        Map<String, Integer> totals = new HashMap<String, Integer>();
        for (LineItem item : items) {
            Integer current = totals.get(item.getProduct());
            totals.put(item.getProduct(), (current == null ? 0 : current) + item.getQuantity());
        }
        return totals;
    }

    public BigDecimal shippingCost(String region) {
        if ("DOMESTIC".equals(region)) {
            return new BigDecimal("5.00");
        } else if ("EU".equals(region)) {
            return new BigDecimal("15.00");
        } else if ("INTL".equals(region)) {
            return new BigDecimal("30.00");
        }
        throw new IllegalArgumentException("Unknown region: " + region);
    }
}
