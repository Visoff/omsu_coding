package ru.visoff;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonComparator {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static boolean jsonEquals(String json1, String json2) throws IOException {
        JsonNode tree1 = MAPPER.readTree(json1);
        JsonNode tree2 = MAPPER.readTree(json2);
        return tree1.equals(tree2);
    }
}
