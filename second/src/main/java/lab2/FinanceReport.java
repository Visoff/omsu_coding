package lab2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FinanceReport implements Iterable<Payment> {
    private List<Payment> payments;
    private String fio;
    private Date date;

    public FinanceReport
    (Payment[] payments, String fio, Date date)
    throws IllegalArgumentException {
        if (payments == null) {
            throw new IllegalArgumentException("Payments cannot be null");
        }
        if (fio == null) {
            throw new IllegalArgumentException("Fio cannot be null");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        this.fio = fio;
        this.date = date;
        for (Payment p : payments) {
            if (p == null) {
                throw new IllegalArgumentException("Payment cannot be null");
            }
        }
        this.payments = new ArrayList<>(List.of(payments));
    }


    public FinanceReport(FinanceReport paymentReport) throws IllegalArgumentException {
        if (paymentReport == null) {
            throw new IllegalArgumentException("PaymentReport cannot be null");
        }
        this.fio = paymentReport.fio;
        this.date = new Date(paymentReport.date);

        this.payments = new ArrayList<>();
        for (Payment p : paymentReport) {
            this.payments.add(new Payment(p));
        }
    }

    public void add(Payment payment) throws IllegalArgumentException {
        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null");
        }
        payments.add(payment);
    }

    public int length() {
        return payments.size();
    }

    public Payment get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= payments.size()) {
            throw new IndexOutOfBoundsException();
        }
        return payments.get(index);
    }

    public void set(int index, Payment payment)
    throws IllegalArgumentException, IndexOutOfBoundsException {
        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null");
        }
        if (index < 0 || index >= payments.size()) {
            throw new IndexOutOfBoundsException();
        }
        payments.set(index, payment);
    }

    @Override
    public String toString() {
        return String.format(
            "[Автор: %s, дата: %s, Платежи: %s",
            fio,
            date,
            payments
        );
    }

	@Override
	public Iterator<Payment> iterator() {
        return payments.iterator();
	}

	public List<Payment> getPayments() {
		return payments;
	}
	public String getAuthor() {
		return fio;
	}
	public Date getDate() {
		return date;
	}
}
