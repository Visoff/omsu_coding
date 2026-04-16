package ru.visoff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class HouseJsonSerializer {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static String toJson(House house) throws IOException {
        return MAPPER.writeValueAsString(house);
    }

    public static House fromJson(String json) throws IOException {
        return MAPPER.readValue(json, House.class);
    }
}
