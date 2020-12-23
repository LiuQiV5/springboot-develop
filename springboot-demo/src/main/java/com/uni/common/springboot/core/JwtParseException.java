package com.uni.common.springboot.core;

public class JwtParseException extends RuntimeException{

    public JwtParseException(Throwable e) {
        super(e);
    }

    public JwtParseException(String message, Throwable e) {
        super(message, e);
    }

    public JwtParseException(String message) {
        super(message);
    }

    public static JwtParseException create(String key, Object... args) {
        return new JwtParseException(MessageUtil.getText(key, args));
    }

    public static JwtParseException create(Throwable t, String key, Object... args) {
        return new JwtParseException(MessageUtil.getText(key, args), t);
    }
}
