package main;

import lab2.IncomeCertificate;
import lab2.TaxDeclaration;

public class App {
    private static IncomeCertificate createTestCertificate(int year, String name, double[] income) {
        return new IncomeCertificate(year, name, "Test Company", income);
    }

    public static void main(String[] args) {
        double[] income1 = {1000.0, 2000.0, 3000.0, 4000.0, 5000.0, 6000.0, 
                          7000.0, 8000.0, 9000.0, 10000.0, 11000.0, 12000.0};
        IncomeCertificate cert1 = createTestCertificate(2023, "John Doe", income1);

        System.out.println(cert1);
        
        TaxDeclaration declaration = new TaxDeclaration(2023, "John Doe", new IncomeCertificate[]{cert1});

        System.out.println(declaration);
    }
}

