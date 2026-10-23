package ru.visoff;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlatStreamDeserializer extends StdDeserializer<Flat> {

    public FlatStreamDeserializer() {
        super(Flat.class);
    }

    @Override
    public Flat deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        int number = 0;
        double area = 0.0;
        List<Person> owners = new ArrayList<>();

        while (p.nextToken() != JsonToken.END_OBJECT) {
            String field = p.getCurrentName();
            if ("number".equals(field)) {
                p.nextToken();
                number = p.getIntValue();
            } else if ("area".equals(field)) {
                p.nextToken();
                area = p.getDoubleValue();
            } else if ("owners".equals(field)) {
                if (p.nextToken() != JsonToken.START_ARRAY) {
                    throw new IOException("Expected owners array");
                }
                while (p.nextToken() != JsonToken.END_ARRAY) {
                    owners.add(p.readValueAs(Person.class));
                }
            } else {
                p.nextToken();
            }
        }

        return new Flat(number, area, owners);
    }
}
