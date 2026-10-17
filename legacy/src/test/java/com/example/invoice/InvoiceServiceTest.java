package com.example.invoice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 * Legacy test suite (Java 8). Every test reads the shared input files from sample/inputs,
 * so it runs exactly the same input cases as the new-gen suite.
 */
class InvoiceServiceTest {

    private final InvoiceService service = new InvoiceService();

    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/subtotal.csv", numLinesToSkip = 1)
    void subtotal(final String caseId, final String items, String expected) {
        assertEquals(expected, Recorder.capture(caseId, new Recorder.Call() {
            public Object run() {
                return service.subtotal(Recorder.items(items));
            }
        }));
    }

    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/discount.csv", numLinesToSkip = 1)
    void applyDiscount(final String caseId, final String amount, final int percent, String expected) {
        assertEquals(expected, Recorder.capture(caseId, new Recorder.Call() {
            public Object run() {
                return service.applyDiscount(new BigDecimal(amount), percent);
            }
        }));
    }

    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/tax.csv", numLinesToSkip = 1)
    void tax(final String caseId, final String amount, String expected) {
        assertEquals(expected, Recorder.capture(caseId, new Recorder.Call() {
            public Object run() {
                return service.tax(new BigDecimal(amount));
            }
        }));
    }

    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/display_name.csv", numLinesToSkip = 1)
    void customerDisplayName(final String caseId, final String firstName, final String lastName, String expected) {
        assertEquals(expected, Recorder.capture(caseId, new Recorder.Call() {
            public Object run() {
                return service.customerDisplayName(firstName, lastName);
            }
        }));
    }

    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/top_products.csv", numLinesToSkip = 1)
    void topProducts(final String caseId, final String items, final int n, String expected) {
        assertEquals(expected, Recorder.capture(caseId, new Recorder.Call() {
            public Object run() {
                return service.topProducts(Recorder.items(items), n);
            }
        }));
    }

    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/quantity.csv", numLinesToSkip = 1)
    void quantityByProduct(final String caseId, final String items, String expected) {
        assertEquals(expected, Recorder.capture(caseId, new Recorder.Call() {
            public Object run() {
                return service.quantityByProduct(Recorder.items(items));
            }
        }));
    }

    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/shipping.csv", numLinesToSkip = 1)
    void shippingCost(final String caseId, final String region, String expected) {
        assertEquals(expected, Recorder.capture(caseId, new Recorder.Call() {
            public Object run() {
                return service.shippingCost(region);
            }
        }));
    }
}
