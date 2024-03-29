package org.neogroup.warp.messages;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.neogroup.warp.Warp.getLogger;

public abstract class Messages {

    private static final String DEFAULT_BUNDLE_FOLDER_NAME = "localization";
    private static final String DEFAULT_BUNDLE_NAME = "main";
    private static final String BUNDLE_NAME_SEPARATOR = ".";

    private static String messagesResourcesFolder = DEFAULT_BUNDLE_FOLDER_NAME;

    public static String getMessagesResourcesFolder() {
        return messagesResourcesFolder;
    }

    public static void setMessagesResourcesFolder(String messagesResourcesFolder) {
        Messages.messagesResourcesFolder = messagesResourcesFolder;
    }

    public static String get(String key, Object... args) {
        return get(Locale.getDefault(), key, args);
    }

    public static String get(Locale locale, String key, Object... args) {
        int index = key.lastIndexOf(BUNDLE_NAME_SEPARATOR);
        String bundleName = null;
        String bundleKey = null;
        if (index < 0) {
            bundleName = DEFAULT_BUNDLE_NAME;
            bundleKey = key;
        }
        else {
            bundleName = key.substring(0, index);
            bundleKey = key.substring(index+1);
        }
        if (messagesResourcesFolder != null) {
            bundleName = messagesResourcesFolder + BUNDLE_NAME_SEPARATOR + bundleName;
        }
        return get(bundleName, locale, bundleKey, args);
    }

    public static String get(String bundleName, String key, Object... args) {
        return get(bundleName, Locale.getDefault(), key, args);
    }

    public static String get(String bundleName, Locale locale, String key, Object... args) {
        String value = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
            if (!bundle.containsKey(key)) {
                throw new RuntimeException("Message key \"" + key + "\" not found in bundle \"" + bundleName + "\" and locale \"" + locale + "\"");
            }
            value = MessageFormat.format(bundle.getString(key), args);
        }
        catch (Exception ex) {
            value = "{" + key + "}";
            getLogger().warn("Missing resource !!", ex);
        }
        return value;
    }
}
