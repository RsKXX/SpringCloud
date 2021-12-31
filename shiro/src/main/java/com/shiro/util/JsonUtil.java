package com.shiro.util;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import com.shiro.config.JacksonConfig;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


@UtilityClass
@Slf4j
public class JsonUtil {

    public static final ObjectMapper OBJ_MAPPER;

    static {
        OBJ_MAPPER = new ObjectMapper();
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new JacksonConfig.LocalDateTimeSerializer());
        timeModule.addDeserializer(LocalDateTime.class, new JacksonConfig.LocalDateTimeDeserializer());
        OBJ_MAPPER.registerModule(timeModule);
        OBJ_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJ_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJ_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJ_MAPPER.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        OBJ_MAPPER.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        OBJ_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public <T> T read(String string, Class<T> clazz) {
        if (string == null) {
            return null;
        }
        try {
            return OBJ_MAPPER.readValue(string, clazz);
        } catch (IOException e) {
            log.error("read json error", e);
        }
        return null;
    }



    public <T> T read(byte[] array, Class<T> clazz) {
        if (array == null) {
            return null;
        }
        try {
            return OBJ_MAPPER.readValue(array, clazz);
        } catch (IOException e) {
            log.error("read json error", e);
        }
        return null;
    }


    public <T> T read(byte[] array, TypeReference<T> typeRef) {
        if (array == null) {
            return null;
        }
        try {
            return OBJ_MAPPER.readValue(array, typeRef);
        } catch (IOException e) {
            log.error("read json error", e);
        }
        return null;
    }

    public <T> T read(String obj, TypeReference<T> typeRef) {
        if (obj == null) {
            return null;
        }
        try {
            return OBJ_MAPPER.readValue(obj, typeRef);
        } catch (IOException e) {
            log.error("read json error", e);
        }
        return null;
    }

    public <T> T readList(byte[] bytes,TypeReference<T> typeReference) {
        if (bytes == null) {
            return null;
        }
        try {
            return OBJ_MAPPER.readValue(bytes, typeReference);
        } catch (IOException e) {
            log.error("read json error", e);
        }
        return null;
    }

    public <T> T readList(String str,TypeReference<T> typeReference) {
        if (str == null) {
            return null;
        }
        try {
            return OBJ_MAPPER.readValue(str, typeReference);
        } catch (JsonProcessingException e) {
            log.error("read json error", e);
        }
        return null;
    }

    public String write(Object o) {
        try {
            return OBJ_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("write json error", e);
        }
        return null;
    }

    public byte[] writeBytes(Object o) {
        try {
            return OBJ_MAPPER.writeValueAsString(o).getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            log.error("write json error", e);
        }
        return null;
    }
}