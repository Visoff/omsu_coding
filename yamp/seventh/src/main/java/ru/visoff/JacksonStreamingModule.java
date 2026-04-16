package ru.visoff;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class JacksonStreamingModule extends SimpleModule {

    public JacksonStreamingModule() {
        addSerializer(Person.class, new PersonFullNameSerializer());
        addDeserializer(Person.class, new PersonFullNameDeserializer());
    }
}
