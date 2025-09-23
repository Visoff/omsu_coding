package lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FinanceReportTest {
    @Test
    public void test() {
        FinanceReport paymentReport = new FinanceReport(
                "Ilya",
                1,
                1,
                2000
                );
        Payment payment = new Payment();
        payment.setFio("Ilya");
        payment.setSum(1000.0);
        payment.setDay(1);
        payment.setMonth(1);
        payment.setYear(2000);
        paymentReport.add(payment);
        assertEquals("[Автор: Ilya, дата: 1.1.2000, Платежи: [Плательщик: Ilya, дата: 1.1.2000, сумма: 1000 руб. 0 коп.]]", paymentReport.toString());
    }
}
