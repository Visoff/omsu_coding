package lab2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class FinanceReportProcessor {
    private static Payment[] findBy(FinanceReport report, Function<Payment, Boolean> condition) {
        List<Payment> res = new ArrayList<>();
        for (int i = 0; i < report.length(); i++) {
            if (condition.apply(report.get(i))) {
                res.add(report.get(i));
            }
        }
        return res.toArray(new Payment[0]);
    }

    public static Payment[] findByFioStartingCharacter(FinanceReport report, char character) {
        return findBy(report, payment -> payment.getFio().charAt(0) == character);
    }

    public static Payment[] findBySumLessThan(FinanceReport report, double sum) {
        return findBy(report, payment -> payment.getSum() < sum);
    }

    public static Double totalForDate(FinanceReport report, String date) {
        return report.stream()
            .filter(payment -> payment.getDay() + "." + payment.getMonth() + "." + payment.getYear() == date)
            .map(Payment::getSum)
            .reduce(0.0, Double::sum);
    }

    public static String[] notPaidMonths(FinanceReport report, Integer year) {
        Set<Integer> res = new HashSet<>();
        for (int i = 1; i <= 12; i++) res.add(i);
        for (int i = 0; i < report.length(); i++) {
            if (report.get(i).getYear() == year) {
                res.remove(report.get(i).getMonth());
            }
        }
        String[] month_names = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return res.stream().map(n -> month_names[n]).toArray(String[]::new);
    }
}
