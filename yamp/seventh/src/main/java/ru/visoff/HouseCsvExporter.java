package ru.visoff;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HouseCsvExporter {

    public static void exportToCsv(House house) throws IOException {
        if (house == null) {
            throw new NullPointerException("House can't be null");
        }
        String fileName = "house_" + sanitizeForFileName(house.getCadastralNumber()) + ".csv";
        Path path = Paths.get(fileName);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("Данные о доме");
            writer.newLine();
            writer.write("Кадастровый номер:;" + house.getCadastralNumber());
            writer.newLine();
            writer.write("Адрес:;" + house.getAddress());
            writer.newLine();
            Person elder = house.getElder();
            String elderFullName = elder.getSurname() + " " + elder.getName() + " " + elder.getPatronymic();
            writer.write("Старший по дому:;" + elderFullName);
            writer.newLine();
            writer.newLine();

            writer.write("Данные о квартирах");
            writer.newLine();
            writer.write("No;Площадь, кв. м;Владельцы");
            writer.newLine();

            for (Flat flat : house.getFlats()) {
                StringBuilder ownersStr = new StringBuilder();
                List<Person> owners = flat.getOwners();
                for (int i = 0; i < owners.size(); i++) {
                    if (i > 0) ownersStr.append(",");
                    Person p = owners.get(i);
                    ownersStr.append(p.getSurname())
                             .append(" ")
                             .append(p.getName().charAt(0))
                             .append(".")
                             .append(p.getPatronymic().charAt(0))
                             .append(".");
                }
                writer.write(flat.getNumber() + ";" + flat.getArea() + ";" + ownersStr.toString());
                writer.newLine();
            }
        }
    }

    private static String sanitizeForFileName(String name) {
        return name.replaceAll("[^a-zA-Z0-9\\-_.]", "_");
    }
}
