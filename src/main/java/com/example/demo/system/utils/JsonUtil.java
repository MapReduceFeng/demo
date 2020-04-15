package com.example.demo.system.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JsonUtil {
    private ObjectMapper objectMapper = new ObjectMapper();

    JsonUtil() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);//Include.NON_NULL 属性为NULL 不序列化
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//反序列化时,遇到未知属性(那些没有对应的属性来映射的属性,并且没有任何setter或handler来处理这样的属性)时是否引起结果失败(通过抛JsonMappingException异常)
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider) throws IOException {// 设置返回null转为 空字符串""
                paramJsonGenerator.writeString("");
            }
        });
    }

    public static JsonUtil create() {
        return new JsonUtil();
    }

    public JsonUtil setDateFormat(String pattern) {
        objectMapper.setDateFormat(new SimpleDateFormat(pattern));
        return this;
    }

    public JsonUtil configure(DeserializationFeature feature, boolean state) {
        objectMapper.configure(feature, state);
        return this;
    }

    public JsonUtil setPropertyNamingStrategy(PropertyNamingStrategy propertyNamingStrategy) {
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
            throw new RuntimeException("c", e);
        }
    }

    public JsonNode getJsonNode(String jsonStr) {
        try {
            return objectMapper.readTree(jsonStr);
        } catch (Exception e) {
            throw new RuntimeException("mapper.getSerializerProvider().setNullValueSerializer(", e);
        }
    }

}
