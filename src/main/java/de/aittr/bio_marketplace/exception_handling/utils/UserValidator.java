package de.aittr.bio_marketplace.exception_handling.utils;

import de.aittr.bio_marketplace.exception_handling.exceptions.EmailValidateException;
import de.aittr.bio_marketplace.exception_handling.exceptions.NumberValidateException;
import de.aittr.bio_marketplace.exception_handling.exceptions.PasswordValidateException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator extends RuntimeException{

    private static final Pattern PHONE_PATTERN = Pattern.compile("^[-+]?[0-9]+$");

    public static void isEmailValid(String email) throws EmailValidateException {

        int indexAt = email.indexOf('@');
        if (indexAt == -1 || indexAt != email.lastIndexOf('@')) throw new EmailValidateException("@ error");

        if (indexAt < 3) throw new EmailValidateException("less than 3 symbols before @");

        for (int i = 1; i <= 3; i++) {
            if (indexAt + i >= email.length()) {
                throw new EmailValidateException("less than 3 symbols after @");
            }
            if (email.charAt(indexAt + i) == '.') {
                throw new EmailValidateException("dot found in first 3 symbols after @");
            }
        }

        int dotIndexAfterAt = email.indexOf('.', indexAt + 1);
        if (dotIndexAfterAt == -1) throw new EmailValidateException(". after @ error");

        int lastDotIndex = email.lastIndexOf('.');
        if (lastDotIndex + 2 >= email.length()) throw new EmailValidateException("last . error");


        for (int i = 0; i < email.length(); i++) {
            char ch = email.charAt(i);

            boolean isPass = (Character.isAlphabetic(ch) ||
                    Character.isDigit(ch) ||
                    ch == '-' ||
                    ch == '_' ||
                    ch == '.' ||
                    ch == '@');

            if (!isPass) throw new EmailValidateException("illegal symbol");

        }

        if (indexAt == 0) throw new EmailValidateException("@ should not first");

        char firstChar = email.charAt(0);
        if (!Character.isAlphabetic(firstChar)) throw new EmailValidateException("first symbol should be alphabetic");


    }

    public static void isPasswordValid(String password)  throws PasswordValidateException {

        if (password == null || password.length() < 8) {
            throw new PasswordValidateException("Password should be at least 8 characters");
        }

        boolean isDigit = false;
        boolean isUpperCase = false;
        boolean isLowerCase = false;
        boolean isSpecialSymbol = false;

        String symbols = "!%$@&*()[].,-";

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isDigit(ch)) isDigit = true;
            if (Character.isUpperCase(ch)) isUpperCase = true;
            if (Character.isLowerCase(ch)) isLowerCase = true; //
            if (symbols.indexOf(ch) >= 0) isSpecialSymbol = true;

        }

        System.out.printf("%s | %s | %s | %s\n", isDigit, isUpperCase, isLowerCase, isSpecialSymbol);

        if (!isDigit) throw new PasswordValidateException("should be a digit in password");
        if (!isLowerCase) throw new PasswordValidateException("should be a lower case letter in password");
        if (!isUpperCase) throw new PasswordValidateException("should be a upper case letter in password");
        if (!isSpecialSymbol) throw new PasswordValidateException("should be a special symbol in password");
    }

    public static void isValidPhoneNumber(String phoneNumber) throws NumberValidateException {
        if (phoneNumber == null) throw new NumberValidateException("Number cannot be null") ;

        if (phoneNumber.length() > 12) {
            throw new NumberValidateException("Phone number cannot exceed 12 characters. Current length: " + phoneNumber.length());
        }
        if (phoneNumber.length() < 9) {
            throw new NumberValidateException("Phone number must have at least 9 digits. Current length: " + phoneNumber.length());
        }

        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        if (!matcher.matches()) {
            throw new NumberValidateException("Phone number contains invalid characters. Only digits, '+' and '-' are allowed.");
        }

        int numberLength = phoneNumber.replaceAll("[^0-9]", "").length();
        if (numberLength > 12) {
            throw new NumberValidateException("Phone number cannot exceed 12 digits. Current length: " + numberLength);
        }
    }

}