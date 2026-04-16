package ru.visoff;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class PersonFullNameDeserializer extends StdDeserializer<Person> {

    public PersonFullNameDeserializer() {
        super(Person.class);
    }

    @Override
    public Person deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String fullName = null;
        LocalDate birthDate = null;

        while (p.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = p.getCurrentName();
            p.nextToken();
            if ("fullName".equals(fieldName)) {
                fullName = p.getText();
            } else if ("birthDate".equals(fieldName)) {
                birthDate = p.readValueAs(LocalDate.class);
            }
        }

        if (fullName == null || birthDate == null) {
            throw new IOException("Missing required fields: fullName or birthDate");
        }

        String[] parts = fullName.split(" ", 3);
        String surname = parts[0];
        String name = parts.length > 1 ? parts[1] : "";
        String patronymic = parts.length > 2 ? parts[2] : "";

        return new Person(surname, name, patronymic, birthDate);
    }
}
