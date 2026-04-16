package ru.visoff;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonComparatorTest {

    @Test
    void jsonEquals_identicalObjects_returnsTrue() throws IOException {
        String json1 = "{\"name\":\"John\",\"age\":30,\"address\":{\"city\":\"NYC\"}}";
        String json2 = "{\"age\":30,\"address\":{\"city\":\"NYC\"},\"name\":\"John\"}";
        assertTrue(JsonComparator.jsonEquals(json1, json2));
    }

    @Test
    void jsonEquals_differentValues_returnsFalse() throws IOException {
        String json1 = "{\"a\":1,\"b\":2}";
        String json2 = "{\"a\":1,\"b\":3}";
        assertFalse(JsonComparator.jsonEquals(json1, json2));
    }

    @Test
    void jsonEquals_differentStructure_returnsFalse() throws IOException {
        String json1 = "{\"x\":[1,2]}";
        String json2 = "{\"x\":[2,1]}";
        assertFalse(JsonComparator.jsonEquals(json1, json2));
    }

    @Test
    void jsonEquals_nestedArrays() throws IOException {
        String json1 = "{\"arr\":[{\"id\":1},{\"id\":2}]}";
        String json2 = "{\"arr\":[{\"id\":2},{\"id\":1}]}";
        assertFalse(JsonComparator.jsonEquals(json1, json2));
    }
}
