package ru.visoff;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HouseStreamDeserializer extends StdDeserializer<House> {

    public HouseStreamDeserializer() {
        super(House.class);
    }

    @Override
    public House deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String cadastralNumber = null;
        String address = null;
        Person elder = null;
        List<Flat> flats = new ArrayList<>();

        while (p.nextToken() != JsonToken.END_OBJECT) {
            String field = p.getCurrentName();
            if ("cadastralNumber".equals(field)) {
                p.nextToken();
                cadastralNumber = p.getText();
            } else if ("address".equals(field)) {
                p.nextToken();
                address = p.getText();
            } else if ("elder".equals(field)) {
                if (p.nextToken() != JsonToken.START_OBJECT) {
                    throw new IOException("Expected elder object");
                }
                elder = p.readValueAs(Person.class);
            } else if ("flats".equals(field)) {
                if (p.nextToken() != JsonToken.START_ARRAY) {
                    throw new IOException("Expected flats array");
                }
                while (p.nextToken() != JsonToken.END_ARRAY) {
                    flats.add(p.readValueAs(Flat.class));
                }
            } else {
                p.nextToken();
            }
        }

        if (cadastralNumber == null || address == null || elder == null) {
            throw new IOException("Missing required fields: cadastralNumber, address or elder");
        }

        return new House(cadastralNumber, address, elder, flats);
    }
}
