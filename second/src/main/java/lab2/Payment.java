package lab2;

import java.util.Objects;

public class Payment {
    private String fio;
    private Date date;
    private Double sum;

    public Payment
    (String fio, Date date, Double sum)
    throws IllegalArgumentException {
        if (fio == null || fio.trim().isEmpty()) {
            throw new IllegalArgumentException("Fio cannot be empty");
        }
        this.fio = fio;
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        this.date = date;
        if (sum == null || sum < 0) {
            throw new IllegalArgumentException("Sum cannot be null");
        }
        if (sum < 0) {
            throw new IllegalArgumentException();
        }
        this.sum = sum;
    }

    public Payment() {
    }

    public Payment(Payment p) {
        this.fio = p.fio;
        this.date = new Date(p.date);
        this.sum = p.sum;
    }

    public boolean equals(Payment p) {
        return fio.equals(p.fio) &&
            Objects.equals(p.date, this.date) &&
            sum.equals(p.sum);
    }

    public int hashCode() {
        return fio.hashCode()+date.hashCode()+sum.hashCode();
    }

    public String toString() {
        final double HUNDRED = 100;
        return String.format(
            "Плательщик: %s, дата: %s, сумма: %d руб. %02d коп.",
            fio,
            date,
            sum.intValue(),
            Double.valueOf((sum * HUNDRED) % HUNDRED).intValue());
    }

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getFio() {
		return fio;
	}
	public void setFio(String fio) {
		this.fio = fio;
	}
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}

}
