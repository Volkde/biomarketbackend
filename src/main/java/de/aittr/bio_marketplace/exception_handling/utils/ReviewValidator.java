package de.aittr.bio_marketplace.exception_handling.utils;

import de.aittr.bio_marketplace.exception_handling.exceptions.ReviewValidationException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReviewValidator extends RuntimeException {

    private static final Pattern DIGITS_ONLY_PATTERN = Pattern.compile("\\d+");

    public static void isValidRating(BigDecimal rating) throws ReviewValidationException {
        if (rating == null) {
            throw new ReviewValidationException("Rating cannot be null.");
        }

        String ratingString = rating.toPlainString();

        if (ratingString.contains(".")) {
            throw new ReviewValidationException("Rating must be an integer.");
        }

        Matcher matcher = DIGITS_ONLY_PATTERN.matcher(ratingString);
        if (!matcher.matches()) {
            throw new ReviewValidationException("Rating contains invalid characters. Only digits are allowed.");
        }

        try {
            int ratingInt = Integer.parseInt(ratingString);
            if (ratingInt < 0 || ratingInt > 5) {
                throw new ReviewValidationException("Rating must be between 0 and 5.");
            }
        } catch (NumberFormatException e) {
            throw new ReviewValidationException("Invalid rating format.  Must be an integer.");
        }
    }
}
