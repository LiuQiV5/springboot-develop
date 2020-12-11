package com.uni.common.springboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.uni.common.springboot.config.CustomizeNullJsonSerializer;
import com.uni.common.springboot.config.MyBeanSerializerModifier;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.setSerializerFactory(OBJECT_MAPPER.getSerializerFactory().withSerializerModifier(new MyBeanSerializerModifier()));
        SerializerProvider serializerProvider = OBJECT_MAPPER.getSerializerProvider();
        serializerProvider.setNullValueSerializer(new CustomizeNullJsonSerializer.NullObjectJsonSerializer());
    }

    @Getter
    @Setter
    @Builder
    private static class User {
        private Long age;
        private String name;
        private List<String> hobby;
        private Boolean marriage;
        private Address address;
    }

    @Getter
    @Setter
    @Builder
    private static class Address {
        private String detail;
    }


    @Test
    public void nullStringJsonSerializerTest() throws JsonProcessingException {
        User user = User
                .builder()
                .name(null)
                .age(26l)
                .marriage(true)
                .hobby(Collections.singletonList("足球"))
                .address(Address.builder().detail("江阴").build())
                .build();
        String value = OBJECT_MAPPER.writeValueAsString(user);
        assertThat(value).contains("");
        assertThat(value).doesNotContain("null");
    }

    @Test
    public void NullNumberJsonSerializer() throws JsonProcessingException {
        User user = User
                .builder()
                .name("小明")
                .age(null)
                .marriage(true)
                .hobby(Collections.singletonList("足球"))
                .address(Address.builder().detail("江阴").build())
                .build();
        String value = OBJECT_MAPPER.writeValueAsString(user);
        assertThat(value).contains("0");
        assertThat(value).doesNotContain("null");
    }

    @Test
    public void NullBooleanJsonSerializer() throws JsonProcessingException {
        User user = User
                .builder()
                .name("小明")
                .age(26l)
                .marriage(null)
                .hobby(Collections.singletonList("足球"))
                .address(Address.builder().detail("江阴").build())
                .build();
        String value = OBJECT_MAPPER.writeValueAsString(user);
        assertThat(value).contains("false");
        assertThat(value).doesNotContain("null");
    }

    @Test
    public void NullObjectJsonSerializer() throws JsonProcessingException {
        User user = User
                .builder()
                .name("小明")
                .age(26l)
                .marriage(true)
                .hobby(Collections.singletonList("足球"))
                .address(null)
                .build();
        String value = OBJECT_MAPPER.writeValueAsString(user);
        assertThat(value).contains("{}");
        assertThat(value).doesNotContain("null");
    }

    @Test
    public void NullArrayJsonSerializer() throws JsonProcessingException {
        User user = User
                .builder()
                .name("小明")
                .age(26l)
                .marriage(true)
                .hobby(null)
                .address(Address.builder().detail("江阴").build())
                .build();
        String value = OBJECT_MAPPER.writeValueAsString(user);
        assertThat(value).contains("[]");
        assertThat(value).doesNotContain("null");
    }
}
