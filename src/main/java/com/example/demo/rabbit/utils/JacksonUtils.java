package com.example.demo.rabbit.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JacksonUtils {
    private ObjectMapper objectMapper = new ObjectMapper();

    JacksonUtils() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider) throws IOException {// 设置返回null转为 空字符串""
                paramJsonGenerator.writeString("");
            }
        });
    }

    public static JacksonUtils create() {
        return new JacksonUtils();
    }

    public JacksonUtils setDateFormat(String pattern) {
        objectMapper.setDateFormat(new SimpleDateFormat(pattern));
        return this;
    }

    public JacksonUtils configure(DeserializationFeature feature, boolean state) {
        objectMapper.configure(feature, state);
        return this;
    }

    public JacksonUtils setPropertyNamingStrategy(PropertyNamingStrategy propertyNamingStrategy) {
        objectMapper.setPropertyNamingStrategy(propertyNamingStrategy);
        return this;
    }

    public String objectToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to json", e);
        }
    }

    public <T> T jsonToObject(String responseJson, Class<T> clazz) {
        try {
            return objectMapper.readValue(responseJson, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert json to object", e);
        }
    }

    public <T> T jsonToObject(String responseJson, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(responseJson, valueTypeRef);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert json to object", e);
        }
    }

    public <T> T jsonJavaBean(JsonNode jsonNode, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonNode.toString(), clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert json to object", e);
        }
    }

    public JsonNode getJsonNode(String jsonStr) {
        try {
            return objectMapper.readTree(jsonStr);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert request to JsonNode", e);
        }
    }

}
