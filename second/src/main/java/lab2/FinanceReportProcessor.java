package lab2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class FinanceReportProcessor {
    private static Payment[] findBy(FinanceReport report, Function<Payment, Boolean> condition) {
        return report.stream()
            .filter(p -> condition.apply(p))
            .toArray(Payment[]::new);
    }

    public static Payment[] findByFioStartingCharacter(FinanceReport report, char character) throws IllegalArgumentException {
        if (report == null) throw new IllegalArgumentException();
        return findBy(report, payment -> payment.getFio().charAt(0) == character);
    }

    public static Payment[] findBySumLessThan(FinanceReport report, double sum) throws IllegalArgumentException {
        if (report == null) throw new IllegalArgumentException();
        return findBy(report, payment -> payment.getSum() < sum);
    }

    public static Double totalForDate(FinanceReport report, String date) throws IllegalArgumentException {
        if (report == null) throw new IllegalArgumentException();
        return report.stream()
            .filter(payment -> date.equals(payment.getDay() + "." + payment.getMonth() + "." + payment.getYear()))
            .map(Payment::getSum)
            .reduce(0.0, Double::sum);
    }

    public static String[] notPaidMonths(FinanceReport report, Integer year) throws IllegalArgumentException {
        if (report == null) throw new IllegalArgumentException();
        Set<Integer> res = new HashSet<>();
        for (int i = 1; i <= 12; i++) res.add(i);
        for (Payment p : report) {
            if (p.getYear().equals(year)) {
                res.remove(p.getMonth());
            }
        }
        String[] month_names = {"", "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        String[] res_arr = new String[res.size()];
        int i = 0;
        for (Integer month : res) {
            res_arr[i] = month_names[month];
            i++;
        }
        return res_arr;
    }
}
