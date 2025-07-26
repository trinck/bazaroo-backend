package org.mts.locationservice.configs;

public class CountryContext {

    private static final ThreadLocal<String> currentCountry = new ThreadLocal<>();
    public static void setCountry(String country) { currentCountry.set(country); }
    public static String getCountry() { return currentCountry.get(); }
    public static void clear() { currentCountry.remove(); }
}
