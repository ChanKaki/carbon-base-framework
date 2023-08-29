package com.carbon.web.mvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 类<code>JacksonMapper</code>说明：
 *
 * @author kaki
 * @since 26/8/2023
 */
public class MyJacksonMapper extends ObjectMapper {

    private final String DEFAULT_FORMAT_TIME = "yyyy-MM-dd hh:mm:ss";

    public MyJacksonMapper() {
        this.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        this.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule simpleModule = new SimpleModule();
        //Serializer
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(long.class, ToStringSerializer.instance);

        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT_TIME);
        simpleModule.addSerializer(Date.class, new CustomDateSerializer(dateFormat));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_FORMAT_TIME);
        simpleModule.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer(dateTimeFormatter));
        //Deserializer
        simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        registerModule(simpleModule);
    }

    class CustomDateSerializer extends JsonSerializer<Date> {

        private final SimpleDateFormat dateFormat;

        public CustomDateSerializer(SimpleDateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException {
            String formattedDate = dateFormat.format(date);
            gen.writeString(formattedDate);
        }
    }

    class CustomLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

        private final DateTimeFormatter dateTimeFormatter;

        public CustomLocalDateTimeSerializer(DateTimeFormatter dateTimeFormatter) {
            this.dateTimeFormatter = dateTimeFormatter;
        }

        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator gen, SerializerProvider provider) throws IOException {
            String formattedDateTime = dateTimeFormatter.format(localDateTime);
            gen.writeString(formattedDateTime);
        }
    }

    class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException {
            long timestamp = p.getValueAsLong();
            if (timestamp == 0L) {
                throw new RuntimeException("入参localDateTime属性存在错误");
            }
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        }
    }
}
