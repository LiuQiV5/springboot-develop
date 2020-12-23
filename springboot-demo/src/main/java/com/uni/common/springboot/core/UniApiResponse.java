package com.uni.common.springboot.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UniApiResponse<T> {
    T data;
    String messageText;

    public UniApiResponse(T data) {
        this.data = data;
    }

    public static <T> UniApiResponse<T> createErrorResponse(String messageText) {
        UniApiResponse<T> response = new UniApiResponse<>();
        response.setMessageText(messageText);
        return response;
    }
}
