package de.aittr.bio_marketplace.exception_handling.utils;

import de.aittr.bio_marketplace.exception_handling.exceptions.AddressValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressValidator {

    private static final Pattern STREET_PATTERN = Pattern.compile("^[a-zA-Z\\u00C0-\\u017F0-9\\-:/ ]+$");
    private static final Pattern COUNTRY_PATTERN = Pattern.compile("^[a-zA-Z\\u00C0-\\u017F-]+$");
    private static final Pattern ZIP_PATTERN = Pattern.compile("^[a-zA-Z\\u00C0-\\u017F0-9]+$");

    public static void isValidZipCode(String zipCode)throws AddressValidationException {
        if (zipCode == null) {
            throw new AddressValidationException("zipCode cannot be null.");
        }

        if (zipCode.trim().isEmpty()) {
            throw new AddressValidationException("zipCode cannot be empty.");
        }

        if (zipCode.length() > 10) {
            throw new AddressValidationException("zipCode cannot exceed 10 characters. Current length: " + zipCode.length());
        }

        Matcher matcher = ZIP_PATTERN.matcher(zipCode);
        if (!matcher.matches()) {
            throw new AddressValidationException("zipCode contains invalid characters. Allows only letters, umlauts and digits.");
        }
    }

    public static void isValidCountry(String country)throws AddressValidationException {
        if (country == null) {
            throw new AddressValidationException("Country cannot be null.");
        }

        if (country.trim().isEmpty()) {
            throw new AddressValidationException("Country cannot be empty.");
        }

        if (country.length() > 30) {
            throw new AddressValidationException("Country cannot exceed 30 characters. Current length: " + country.length());
        }

        Matcher matcher = COUNTRY_PATTERN.matcher(country);
        if (!matcher.matches()) {
            throw new AddressValidationException("Country contains invalid characters. Only letters and the symbol: - are allowed.");
        }

    }

    public static void isValidCity(String city)throws AddressValidationException {
        if (city == null) {
            throw new AddressValidationException("City cannot be null.");
        }

        if (city.trim().isEmpty()) {
            throw new AddressValidationException("City cannot be empty.");
        }

        if (city.length() > 30) {
            throw new AddressValidationException("City cannot exceed 30 characters. Current length: " + city.length());
        }

        Matcher matcher = COUNTRY_PATTERN.matcher(city);
        if (!matcher.matches()) {
            throw new AddressValidationException("City contains invalid characters. Only letters and the symbol: - are allowed.");
        }
    }

    public static void isValidStreet(String street)throws AddressValidationException {
        if (street == null) {
            throw new AddressValidationException("Street cannot be null.");
        }

        if (street.trim().isEmpty()) {
            throw new AddressValidationException("Street cannot be empty.");
        }

        if (street.length() > 30) {
            throw new AddressValidationException("Street cannot exceed 30 characters. Current length: " + street.length());
        }

        Matcher matcher = STREET_PATTERN.matcher(street);
        if (!matcher.matches()) {
            throw new AddressValidationException("Street contains invalid characters. Only digits, letters and symbols: -, /, : are allowed.");
        }

    }


}
