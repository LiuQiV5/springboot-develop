package com.uni.common.springboot.core;

public class ObtainTokenFailedException extends RuntimeException {

    public ObtainTokenFailedException(Throwable e) {
        super(e);
    }

    public ObtainTokenFailedException(String message, Throwable e) {
        super(message, e);
    }

    public ObtainTokenFailedException(String message) {
        super(message);
    }

    public static ObtainTokenFailedException create(String key, Object... args) {
        return new ObtainTokenFailedException(MessageUtil.getText(key, args));
    }

    public static ObtainTokenFailedException create(Throwable t, String key, Object... args) {
        return new ObtainTokenFailedException(MessageUtil.getText(key, args), t);
    }
}
