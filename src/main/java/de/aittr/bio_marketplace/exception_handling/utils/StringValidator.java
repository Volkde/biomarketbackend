package de.aittr.bio_marketplace.exception_handling.utils;
import de.aittr.bio_marketplace.exception_handling.exceptions.AddressValidationException;
import de.aittr.bio_marketplace.exception_handling.exceptions.UsernameValidateException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidator extends RuntimeException{

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-ZäöüÄÖÜß\\s]{1,30}$");
    private static final Pattern DIGITS_ONLY_PATTERN = Pattern.compile("\\d+");
    private static final Pattern LETTERS_ONLY_PATTERN = Pattern.compile("[a-zA-Z]+");

    public static void isValidName(String name)throws UsernameValidateException {
        if (name == null) {
            throw new UsernameValidateException("Name cannot be null.");
        }

        if (name.trim().isEmpty()) {
            throw new UsernameValidateException("Name cannot be empty.");
        }

        if (name.length() > 30) {
            throw new UsernameValidateException("Name cannot exceed 30 characters. Current length: " + name.length());
        }

        Matcher matcher = NAME_PATTERN.matcher(name);
        if (!matcher.matches()) {
            throw new UsernameValidateException("Name contains invalid characters. Only letters and spaces are allowed.");
        }

    }

    public static void isValidZipCode(String zipCode)throws AddressValidationException {
        if (zipCode == null) {
            throw new AddressValidationException("zipCode cannot be null.");
        }

        if (zipCode.trim().isEmpty()) {
            throw new AddressValidationException("zipCode cannot be empty.");
        }

        if (zipCode.length() > 10) {
            throw new AddressValidationException("zipCode cannot exceed 30 characters. Current length: " + zipCode.length());
        }

        Matcher matcher = DIGITS_ONLY_PATTERN.matcher(zipCode);
        if (!matcher.matches()) {
            throw new AddressValidationException("zipCode contains invalid characters. Only digits are allowed.");
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

            Matcher matcher = LETTERS_ONLY_PATTERN.matcher(country);
            if (!matcher.matches()) {
                throw new AddressValidationException("Country contains invalid characters. Only digits are allowed.");
            }

    }

}
