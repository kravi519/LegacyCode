package com.example.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Runs one call for an input case and prints its actual output as {@code RESULT|<case>|<output>},
 * so the comparator can line up legacy and new-gen outputs for the same input.
 */
public final class Recorder {

    public interface Call {
        Object run() throws Exception;
    }

    private Recorder() {
    }

    public static String capture(String caseId, Call call) {
        String output;
        try {
            output = render(call.run());
        } catch (Exception e) {
            output = "throws " + e.getClass().getSimpleName();
        }
        System.out.println("RESULT|" + caseId + "|" + output);
        return output;
    }

    /** Parses "Product:quantity:unitPrice;..." into line items; blank means no items. */
    static List<LineItem> items(String spec) {
        List<LineItem> items = new ArrayList<LineItem>();
        if (spec == null || spec.trim().isEmpty()) {
            return items;
        }
        for (String part : spec.split(";")) {
            String[] f = part.trim().split(":");
            items.add(new LineItem(f[0], Integer.parseInt(f[1]), new BigDecimal(f[2])));
        }
        return items;
    }

    private static String render(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).toPlainString();
        }
        if (value instanceof List) {
            StringBuilder sb = new StringBuilder();
            for (Object o : (List<?>) value) {
                if (sb.length() > 0) {
                    sb.append(';');
                }
                sb.append(o);
            }
            return sb.toString();
        }
        if (value instanceof Map) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<?, ?> e : new TreeMap<Object, Object>((Map<?, ?>) value).entrySet()) {
                if (sb.length() > 0) {
                    sb.append(';');
                }
                sb.append(e.getKey()).append('=').append(e.getValue());
            }
            return sb.toString();
        }
        return String.valueOf(value);
    }
}
