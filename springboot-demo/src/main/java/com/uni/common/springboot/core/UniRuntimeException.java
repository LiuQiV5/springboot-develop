package com.uni.common.springboot.core;

public class UniRuntimeException extends RuntimeException {
    public UniRuntimeException(Throwable e) {
        super(e);
    }

    public UniRuntimeException(String message, Throwable e) {
        super(message, e);
    }

    public UniRuntimeException(String message) {
        super(message);
    }

    public static UniRuntimeException create(String key, Object... args) {
        return new UniRuntimeException(MessageUtil.getText(key, args));
    }

    public static UniRuntimeException create(Throwable t, String key, Object... args) {
        return new UniRuntimeException(MessageUtil.getText(key, args), t);
    }
}
