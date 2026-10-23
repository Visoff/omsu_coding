package ru.visoff;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class FlatStreamSerializer extends StdSerializer<Flat> {

    public FlatStreamSerializer() {
        super(Flat.class);
    }

    @Override
    public void serialize(Flat flat, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("number", flat.getNumber());
        gen.writeNumberField("area", flat.getArea());
        gen.writeArrayFieldStart("owners");
        for (Person owner : flat.getOwners()) {
            gen.writeObject(owner);
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
}
