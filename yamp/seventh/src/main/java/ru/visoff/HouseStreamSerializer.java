package ru.visoff;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class HouseStreamSerializer extends StdSerializer<House> {

    public HouseStreamSerializer() {
        super(House.class);
    }

    @Override
    public void serialize(House house, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("cadastralNumber", house.getCadastralNumber());
        gen.writeStringField("address", house.getAddress());
        gen.writeObjectField("elder", house.getElder());
        gen.writeArrayFieldStart("flats");
        for (Flat flat : house.getFlats()) {
            gen.writeObject(flat);
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
}
