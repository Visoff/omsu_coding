package lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PaymentTest {
    @Test
    public void test() {
        Payment payment = new Payment();
        payment.setFio("Ilya");
        payment.setSum(1000.0);
        payment.setDay(1);
        payment.setMonth(1);
        payment.setYear(2000);
        assertEquals("Плательщик: Ilya, дата: 1.1.2000, сумма: 1000 руб. 0 коп.", payment.toString());
    }
}
