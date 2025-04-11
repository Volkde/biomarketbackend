package de.aittr.bio_marketplace.exception_handling.utils;
import de.aittr.bio_marketplace.exception_handling.exceptions.UsernameValidateException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidator extends RuntimeException{

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\u00C0-\\u017F-]+$");
    private static final Pattern STORE_PATTERN = Pattern.compile("^[a-zA-Z\\u00C0-\\u017F0-9\\-:/ ]+$");

    public static void isValidStoreName(String storeName)throws UsernameValidateException {
        if (storeName == null) {
            throw new UsernameValidateException("Store name cannot be null.");
        }

        if (storeName.trim().isEmpty()) {
            throw new UsernameValidateException("Store name cannot be empty.");
        }

        if (storeName.length() > 40) {
            throw new UsernameValidateException("Store name cannot exceed 40 characters. Current length: " + storeName.length());
        }

        Matcher matcher = STORE_PATTERN.matcher(storeName);
        if (!matcher.matches()) {
            throw new UsernameValidateException("Store name contains invalid characters. Only digits, letters and symbols: -, /, : are allowed.");
        }

    }

    public static void isValidFirstName(String firstName)throws UsernameValidateException {
        if (firstName == null) {
            throw new UsernameValidateException("Firstname cannot be null.");
        }

        if (firstName.trim().isEmpty()) {
            throw new UsernameValidateException("Firstname cannot be empty.");
        }

        if (firstName.length() > 30) {
            throw new UsernameValidateException("Firstname cannot exceed 30 characters. Current length: " + firstName.length());
        }

        Matcher matcher = NAME_PATTERN.matcher(firstName);
        if (!matcher.matches()) {
            throw new UsernameValidateException("Firstname contains invalid characters.  Only letters and the symbol: - are allowed.");
        }
    }

    public static void isValidLastName(String lastName)throws UsernameValidateException {
        if (lastName == null) {
            throw new UsernameValidateException("Lastname cannot be null.");
        }

        if (lastName.trim().isEmpty()) {
            throw new UsernameValidateException("Lastname cannot be empty.");
        }

        if (lastName.length() > 30) {
            throw new UsernameValidateException("Lastname cannot exceed 30 characters. Current length: " + lastName.length());
        }

        Matcher matcher = NAME_PATTERN.matcher(lastName);
        if (!matcher.matches()) {
            throw new UsernameValidateException("Lastname contains invalid characters. Only letters and the symbol: - are allowed.");
        }
    }

}
