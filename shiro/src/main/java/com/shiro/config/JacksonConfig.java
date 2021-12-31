package com.shiro.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * @author : guoxinze
 * Date: 2021/5/31
 * Time: 17:40
 * Description: 这里就不自动注入了
 */
public class JacksonConfig {

    /**
     * 统一配置类型的转换策略
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            //将Long类型转换成string类型返回，避免大整数导致前端精度丢失的问题
//            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
//            builder.serializerByType(Long.class,ToStringSerializer.instance);
            //将LocalDateTime全局返回时间戳（方便前端处理）并且将参数里面的时间戳转换成LocalDateTime
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer());
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer());
        };
    }

    /**
     * LocalDateTime转换为时间戳
     */
    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            if (value != null) {
                long timestamp = value.toInstant(ZoneOffset.of("+8")).toEpochMilli();
                gen.writeNumber(timestamp);
            }
        }
    }

    /**
     * 时间戳转换LocalDateTime
     */
    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext)
                throws IOException {
            long timestamp = p.getValueAsLong();
            if (timestamp > 0) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.of("+8"));
            } else {
                return null;
            }
        }
    }
}
