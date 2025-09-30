package lab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FinanceReport {
    private List<Payment> payments;
    private String fio;
    private Integer day, month, year;

    public FinanceReport(Payment payments[], String fio, Integer day, Integer month, Integer year) throws IllegalArgumentException {
        if (payments == null || fio == null || day == null || month == null || year == null) throw new IllegalArgumentException();
        this.fio = fio;
        this.day = day;
        this.month = month;
        this.year = year;
        this.payments = Arrays.asList(payments);
    }

    public FinanceReport(FinanceReport paymentReport) {
        this.fio = paymentReport.fio;
        this.day = paymentReport.day;
        this.month = paymentReport.month;
        this.year = paymentReport.year;
        this.payments = new ArrayList<>();
        for (Payment payment : paymentReport.payments) {
            this.payments.add(new Payment(payment));
        }
    }

    public Stream<Payment> stream() {
        return payments.stream();
    }

    public void add(Payment payment) {
        payments.add(payment);
    }

    public int length() {
        return payments.size();
    }

    public Payment get(int index) {
        return payments.get(index);
    }

    public void set(int index, Payment payment) {
        payments.set(index, payment);
    }

    public String toString() {
        return String.format("[Автор: %s, дата: %d.%d.%d, Платежи: %s", fio, day, month, year, payments);
    }

    public String getAuthor() {
        return fio;
    }
}
