package com.speechpro.biometric.platform.onepass.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by sadurtinova on 09.09.2016.
 */

public class JsonSerializer {
    public static <T> String serialize(T instance) {

        try {
            return serialize(instance, false);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String serialize(T instance, boolean pretty) throws IOException {
        String result = null;

        if (instance != null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JSR310Module());
            StringWriter writer = new StringWriter();

            if (pretty) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(writer, instance);
            } else {
                mapper.writeValue(writer, instance);
            }

            result = writer.toString();
        }

        return result;
    }

    public static <T> T deserialize(String json, Class<T> type) throws IOException {
        T result = null;

        if (json != null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JSR310Module());

            result = mapper.readValue(json, type);
        }

        return result;
    }

    public static <T> T clone(T instance) {
        try {
            return (T) deserialize(serialize(instance), instance.getClass());
        } catch (IOException e) {
            return null;
        }
    }

    public static <S, D> D convert(S source, Class<D> destinationClass) throws IOException {
        return deserialize(serialize(source), destinationClass);
    }
}
