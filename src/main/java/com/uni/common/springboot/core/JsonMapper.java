package com.uni.common.springboot.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class JsonMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T readValue(String jsonString, Class<T> clz) {
        try {
            return StringUtils.isEmpty(jsonString) ? null : objectMapper.readValue(jsonString, clz);
        }
        catch (IOException e) {
            throw new UniRuntimeException(e);
        }
    }

    public <T> T readValue(String jsonString, TypeReference<T> typeReference) {
        try {
            return StringUtils.isEmpty(jsonString) ? null : objectMapper.readValue(jsonString, typeReference);
        }
        catch (IOException e) {
            throw new UniRuntimeException(e);
        }
    }

    public String writeValueAsString(Object object) {
        try {
            return object == null ? null : objectMapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new UniRuntimeException(e);
        }
    }
}

