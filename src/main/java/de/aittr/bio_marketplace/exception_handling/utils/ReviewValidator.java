package de.aittr.bio_marketplace.exception_handling.utils;
import de.aittr.bio_marketplace.exception_handling.exceptions.ReviewValidationException;

import java.math.BigDecimal;


public class ReviewValidator extends RuntimeException {
    public static void isValidRating(BigDecimal rating) throws ReviewValidationException {
        if (rating == null) {
            throw new ReviewValidationException("Rating cannot be null.");
        }

        String ratingString = rating.toString();

        if (ratingString.contains(".")) {
            throw new ReviewValidationException("Rating must be an integer.");
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

