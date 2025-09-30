package lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentTest {
    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = new Payment("Иванов Иван Иванович", 15, 12, 2023, 150000.0);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("Иванов Иван Иванович", payment.getFio());
        assertEquals(15, payment.getDay());
        assertEquals(12, payment.getMonth());
        assertEquals(2023, payment.getYear());
        assertEquals(150000, payment.getSum());
    }

    @Test
    void testSetters() {
        payment.setFio("Петров Петр Петрович");
        payment.setDay(20);
        payment.setMonth(5);
        payment.setYear(2024);
        payment.setSum(75000.0);

        assertEquals("Петров Петр Петрович", payment.getFio());
        assertEquals(20, payment.getDay());
        assertEquals(5, payment.getMonth());
        assertEquals(2024, payment.getYear());
        assertEquals(75000.0, payment.getSum());
    }

    @Test
    void testEquals_SameObject() {
        assertEquals(payment, payment);
    }

    @Test
    void testEquals_EqualObjects() {
        Payment samePayment = new Payment("Иванов Иван Иванович", 15, 12, 2023, 150000.0);
        assertTrue(payment.equals(samePayment));
    }

    @Test
    void testEquals_DifferentObjects() {
        Payment differentPayment = new Payment("Петров Петр Петрович", 20, 5, 2024, 75000.0);
        assertNotEquals(payment, differentPayment);
    }

    @Test
    void testEquals_Null() {
        assertNotEquals(null, payment);
    }

    @Test
    void testEquals_DifferentClass() {
        assertNotEquals(payment, "Not a Payment object");
    }

    @Test
    void testHashCode_EqualObjectsSameHashCode() {
        Payment samePayment = new Payment("Иванов Иван Иванович", 15, 12, 2023, 150000.0);
        assertEquals(payment.hashCode(), samePayment.hashCode());
    }

    @Test
    void testHashCode_Consistent() {
        int initialHashCode = payment.hashCode();
        assertEquals(initialHashCode, payment.hashCode());
    }

    @Test
    void testToString_Format() {
        String result = payment.toString();
        assertTrue(result.contains("Иванов Иван Иванович"));
        assertTrue(result.contains("15"));
        assertTrue(result.contains("12"));
        assertTrue(result.contains("2023"));
        assertTrue(result.contains("150000 руб. 00 коп."));
    }


    @Test
    void testAmountValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Payment("Test", 1, 1, 2023, -100.0));
    }

    @Test
    void testDateValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Payment("Test", 32, 1, 2023, 1000.0));
        assertThrows(IllegalArgumentException.class, () -> new Payment("Test", 1, 13, 2023, 1000.0));
    }
}
