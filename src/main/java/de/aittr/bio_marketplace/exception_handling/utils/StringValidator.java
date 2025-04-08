package de.aittr.bio_marketplace.exception_handling.utils;
import de.aittr.bio_marketplace.exception_handling.exceptions.UsernameValidateException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidator extends RuntimeException{

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-ZäöüÄÖÜß\\s]{1,30}$");

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

}
