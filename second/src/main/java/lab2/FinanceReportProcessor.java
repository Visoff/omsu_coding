package lab2;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.StreamSupport;

public class FinanceReportProcessor {
    private static final int POSSIBLE_MONTHS = 12;

    private static Payment[]
    findBy(FinanceReport report, Function<Payment, Boolean> condition)
    throws IllegalArgumentException {
        if (report == null) {
            throw new IllegalArgumentException("Report cannot be null");
        }
        return StreamSupport.stream(report.spliterator(), false)
                .filter(p -> condition.apply(p))
                .toArray(Payment[]::new);
    }

    public static Payment[] findByFioStartingCharacter(FinanceReport report, char character)
            throws IllegalArgumentException {
        if (report == null) {
            throw new IllegalArgumentException("Report cannot be null");
        }
        return findBy(report, payment -> payment.getFio().charAt(0) == character);
    }

    public static Payment[]
    findBySumLessThan(FinanceReport report, double sum)
    throws IllegalArgumentException {
        if (report == null) {
            throw new IllegalArgumentException("Report cannot be null");
        }
        if (sum < 0) {
            throw new IllegalArgumentException("Sum cannot be negative");
        }
        return findBy(report, payment -> payment.getSum() < sum);
    }

    public static Double
    totalForDate(FinanceReport report, String date)
    throws IllegalArgumentException {
        if (report == null) {
            throw new IllegalArgumentException("Report cannot be null");
        }
        if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException("Date cannot be empty");
        }
        return StreamSupport.stream(report.spliterator(), false)
                .filter(payment -> date.equals(payment.getDate().toString()))
                .map(Payment::getSum)
                .reduce(0.0, Double::sum);
    }

    public static String[]
    notPaidMonths(FinanceReport report, Integer year)
    throws IllegalArgumentException {
        if (report == null) {
            throw new IllegalArgumentException();
        }
        Set<Integer> res = new HashSet<>();
        for (int i = 1; i <= POSSIBLE_MONTHS; i++) {
            res.add(i);
        }
        for (Payment p : report) {
            if (year.equals(p.getDate().getYear())) {
                res.remove(p.getDate().getMonth());
            }
        }
        String[] month_names = {
                "", "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
        };
        String[] res_arr = new String[res.size()];
        int i = 0;
        for (Integer month : res) {
            res_arr[i] = month_names[month];
            i++;
        }
        return res_arr;
    }
}
