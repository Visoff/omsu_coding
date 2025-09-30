package lab2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

class FinanceReportProcessorTest {

    private FinanceReport createTestReport() {
        Payment[] payments = new Payment[] {
                new Payment("Иванов Иван Иванович", 15, 12, 2023, 1500.0),
                new Payment("Петров Петр Петрович", 20, 12, 2023, 750.0),
                new Payment("Сидоров Алексей Николаевич", 5, 1, 2024, 2000.0),
                new Payment("Иванова Мария Сергеевна", 10, 1, 2024, 500.0),
                new Payment("Петрова Ольга Владимировна", 15, 2, 2024, 1250.0)
        };
        return new FinanceReport(Arrays.asList(payments), "Автор", 1, 3, 2024);
    }

    @Test
    void testGetPaymentsBySurnameStartingWith() {
        FinanceReport report = createTestReport();

        Payment[] result = FinanceReportProcessor.findByFioStartingCharacter(report, 'И');
        assertEquals(2, result.length);
        assertEquals("Иванов Иван Иванович", result[0].getFio());
        assertEquals("Иванова Мария Сергеевна", result[1].getFio());

        result = FinanceReportProcessor.findByFioStartingCharacter(report, 'П');
        assertEquals(2, result.length);
        assertEquals("Петров Петр Петрович", result[0].getFio());
        assertEquals("Петрова Ольга Владимировна", result[1].getFio());

        result = FinanceReportProcessor.findByFioStartingCharacter(report, 'X');
        assertEquals(0, result.length);
    }

    @Test
    void testGetPaymentsByAmountLessThan() {
        FinanceReport report = createTestReport();

        Payment[] result = FinanceReportProcessor.findBySumLessThan(report, 1000.0);
        assertEquals(2, result.length);
        assertEquals(750.0, result[0].getSum());
        assertEquals(500.0, result[1].getSum());

        result = FinanceReportProcessor.findBySumLessThan(report, 500.0);
        assertEquals(0, result.length);

        result = FinanceReportProcessor.findBySumLessThan(report, 3000);
        assertEquals(5, result.length);
    }

    @Test
    void testGetTotalPaymentByDate() {
        FinanceReport report = createTestReport();

        Double total = FinanceReportProcessor.totalForDate(report, "15.12.2023");
        assertEquals(1500.0, total);

        total = FinanceReportProcessor.totalForDate(report, "25.12.2023");
        assertEquals(0.0, total);

        Payment[] payments = {
                new Payment("Test1", 15, 12, 2023, 1000.0),
                new Payment("Test2", 15, 12, 2023, 2000.0)
        };
        FinanceReport multiReport = new FinanceReport(Arrays.asList(payments), "Автор", 1, 1, 2024);

        total = FinanceReportProcessor.totalForDate(multiReport, "15.12.2023");
        assertEquals(3000.0, total);
    }

    @Test
    void testGetMonthsWithoutPayments() {
        FinanceReport report = createTestReport();

        String[] emptyMonths = FinanceReportProcessor.notPaidMonths(report, 2023);

        assertEquals(11, emptyMonths.length);

        emptyMonths = FinanceReportProcessor.notPaidMonths(report, 2024);
        assertEquals(10, emptyMonths.length);

        emptyMonths = FinanceReportProcessor.notPaidMonths(report, 2022);
        assertEquals(12, emptyMonths.length);

        boolean hasMarch = false;
        for (String month : emptyMonths) {
            if (month.equals("Март"))
                hasMarch = true;
        }
        assertTrue(hasMarch);
    }

    @Test
    void testEmptyReport() {
        FinanceReport emptyReport = new FinanceReport(new ArrayList<Payment>(), "Автор", 1, 1, 2023);

        Payment[] result = FinanceReportProcessor.findByFioStartingCharacter(emptyReport, 'И');
        assertEquals(0, result.length);

        result = FinanceReportProcessor.findBySumLessThan(emptyReport, 100000);
        assertEquals(0, result.length);

        Double total = FinanceReportProcessor.totalForDate(emptyReport, "01.01.23");
        assertEquals(0.0, total);

        String[] emptyMonths = FinanceReportProcessor.notPaidMonths(emptyReport, 2023);
        assertEquals(12, emptyMonths.length);
    }

    @Test
    void testNullReport() {
        assertThrows(IllegalArgumentException.class,
                () -> FinanceReportProcessor.findByFioStartingCharacter(null, 'И'));

        assertThrows(IllegalArgumentException.class,
                () -> FinanceReportProcessor.findBySumLessThan(null, 1000));

        assertThrows(IllegalArgumentException.class,
                () -> FinanceReportProcessor.totalForDate(null, "01.01.23"));

        assertThrows(IllegalArgumentException.class, () -> FinanceReportProcessor.notPaidMonths(null, 2023));
    }
}
