package org.mts.usersservice.configs;

public class CountryContext {

    private static final ThreadLocal<String> currentCountry = new ThreadLocal<>();
    private static final ThreadLocal<String> currentRealm = new ThreadLocal<>();
    public static void setCountry(String country) { currentCountry.set(country); }
    public static void setRealm(String realm) { currentRealm.set(realm); }
    public static String getCountry() { return currentCountry.get(); }
    public static String getRealm() { return currentRealm.get(); }
    public static void clear() { currentCountry.remove(); currentRealm.remove(); }
}
