package com.meoying.ai.ielts.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;

import java.io.IOException;
import java.util.Optional;

public class JsonUtils {
    private static final ObjectMapper objectMapper = ObjectMapperFactory.getDefaultObjectMapper();

    public static String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("to Json error", e);
        }
    }

    public static <T> T readObject(String json, TypeReference<T> typeReference) {
        return readObject(json, objectMapper.getTypeFactory().constructType(typeReference));
    }

    public static <T> T readObject(String json, Class<T> clazz) {
        return readObject(json, objectMapper.getTypeFactory().constructType(clazz));
    }

    public static <T> T readObject(byte[] json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalStateException("read Json error", e);
        }
    }

    public static <T> T readObject(String json, JavaType javaType) {
        if (isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new IllegalStateException("read Json error", e);
        }
    }

    public static JsonNode path(String json, String path) {
        if (isBlank(json)) {
            return MissingNode.getInstance();
        }
        try {
            String atPath = path;
            if (!path.startsWith("/")) {
                atPath = "/" + path;
            }
            return objectMapper.readTree(json).at(atPath);
        } catch (IOException e) {
            throw new IllegalStateException("read Json error", e);
        }
    }

    private static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static <T> Optional<T> readPath(String json, String path, TypeReference<T> typeReference) {
        return readPath(json, path, objectMapper.getTypeFactory().constructType(typeReference));
    }

    public static <T> Optional<T> readPath(String json, String path, Class<T> clazz) {
        JsonNode jsonNode = path(json, path);
        if (jsonNode.isMissingNode()) {
            return Optional.empty();
        }
        try {
            return Optional.of(objectMapper.treeToValue(jsonNode, clazz));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T> Optional<T> readPath(String json, String path, JavaType javaType) {
        JsonNode jsonNode = path(json, path);
        if (jsonNode.isMissingNode()) {
            return Optional.empty();
        }
        return Optional.of(objectMapper.convertValue(jsonNode, javaType));
    }
}
