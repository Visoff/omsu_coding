package ru.visoff;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class PersonFullNameSerializer extends StdSerializer<Person> {

    public PersonFullNameSerializer() {
        super(Person.class);
    }

    @Override
    public void serialize(Person person, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        String fullName = person.getSurname() + " " + person.getName() + " " + person.getPatronymic();
        gen.writeStringField("fullName", fullName);
        gen.writeObjectField("birthDate", person.getBirthDate());
        gen.writeEndObject();
    }
}
