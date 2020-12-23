package com.uni.common.springboot.core;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Slf4j
public class MessageUtil {
    private static ResourceBundle bundle;

    public static String getText(String key, Object... args) {
        String textTemplate = getTextTemplate(key);
        return MessageFormat.format(textTemplate, args);
    }

    private static String getTextTemplate(String key) {
        try {
            if (bundle == null)
                bundle = ResourceBundle.getBundle("app_messages");
            return bundle.getString(key);
        }
        catch (MissingResourceException e) {
            log.debug(e.getMessage());
            return key;
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return key;
        }
    }
}
