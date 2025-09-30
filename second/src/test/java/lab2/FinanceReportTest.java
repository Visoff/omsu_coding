package lab2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class FinanceReportTest {
    private FinanceReport report;
    private Payment payments[];

    @BeforeEach
    void setUp() {
        payments = new Payment[] {
            new Payment("Иванов Иван Иванович", 15, 12, 2023, 150.0),
            new Payment("Петров Петр Петрович", 20, 12, 2023, 750.0),
            new Payment("Сидоров Алексей Николаевич", 5, 1, 2024, 200000.0)
        };
        report = new FinanceReport(payments, "Смирнова Анна Владимировна", 25, 1, 2024);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("Смирнова Анна Владимировна", report.getAuthor());
        assertEquals(3, report.length());
        assertEquals(payments[0], report.get(0));
        assertEquals(payments[1], report.get(1));
    }

    @Test
    void testSetPayment() {
        Payment newPayment = new Payment("Козлов Дмитрий Сергеевич", 10, 1, 2024, 100000.0);
        report.set(1, newPayment);

        assertTrue(newPayment.equals(report.get(1)));
        assertEquals("Козлов Дмитрий Сергеевич", report.get(1).getFio());
    }

    @Test
    void testGetPayment_IndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> report.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> report.get(3));
    }

    @Test
    void testSetPayment_IndexOutOfBounds() {
        Payment newPayment = new Payment("Test", 1, 1, 2023, 1000.0);
        assertThrows(IndexOutOfBoundsException.class, () -> report.set(-1, newPayment));
        assertThrows(IndexOutOfBoundsException.class, () -> report.set(3, newPayment));
    }

    @Test
    void testToString_Format() {
        String result = report.toString();

        assertTrue(result.contains("Автор: Смирнова Анна Владимировна"));
        assertTrue(result.contains("дата: 25.1.2024"));
        assertTrue(result.contains("Платежи:"));
        assertTrue(result.contains("Плательщик: Иванов Иван Иванович"));
        assertTrue(result.contains("дата: 15.12.2023, сумма: 150 руб. 00 коп."));
        assertTrue(result.contains("Плательщик: Петров Петр Петрович"));
        assertTrue(result.contains("дата: 20.12.2023, сумма: 750 руб. 00 коп."));
    }

    @Test
    void testCopyConstructor_DeepCopy() {
        FinanceReport copy = new FinanceReport(report);

        // Test that they are equal
        assertEquals(report.getAuthor(), copy.getAuthor());
        assertEquals(report.length(), copy.length());

        // Modify original payment
        Payment originalPayment = report.get(0);
        originalPayment.setSum(99999.0);

        // Copy should not be affected
        assertNotEquals(originalPayment.getSum(), copy.get(0).getSum());
    }

    @Test
    void testEmptyPayments() {
        FinanceReport emptyReport = new FinanceReport(new Payment[0], "Автор", 1, 1, 2023);
        assertEquals(0, emptyReport.length());

        String result = emptyReport.toString();
        assertTrue(result.contains("Платежи: []"));
    }

    @Test
    void testNullPayments() {
        assertThrows(IllegalArgumentException.class, () -> new FinanceReport(null, "Автор", 1, 1, 2023));
    }
}
