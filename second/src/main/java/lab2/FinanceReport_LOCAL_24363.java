package lab2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class FinanceReport implements Iterable<Payment> {
    private List<Payment> payments;
    private String fio;
    private Integer day, month, year;

    public FinanceReport(String fio, Integer day, Integer month, Integer year) {
        this.fio = fio;
        this.day = day;
        this.month = month;
        this.year = year;
        this.payments = new ArrayList<>();
    }


    public FinanceReport(List<Payment> payments, String fio, Integer day, Integer month, Integer year) {
        this.fio = fio;
        this.day = day;
        this.month = month;
        this.year = year;
        this.payments = payments;
    }

    public FinanceReport(FinanceReport paymentReport) {
        this.fio = paymentReport.fio;
        this.day = paymentReport.day;
        this.month = paymentReport.month;
        this.year = paymentReport.year;
        this.payments = new ArrayList<>(paymentReport.payments);
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
        return "[Автор: " + fio + ", дата: " + day + "." + month + "." + year + ", Платежи: " + payments + "]";
    }


	@Override
	public Iterator<Payment> iterator() {
        return payments.iterator();
	}
}
