package hu.mik.pte.bpnh16.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";
    
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "hu";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final Long ORDER_STATUS_FELDOLGOZAS_ALATT = 1351L;
    public static final Long ORDER_STATUS_LEZARVA = 1352L;

    private Constants() {
    }
}
