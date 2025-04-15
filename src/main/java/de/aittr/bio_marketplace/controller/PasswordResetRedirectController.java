package de.aittr.bio_marketplace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reset-password")
public class PasswordResetRedirectController {

    @GetMapping
    public ResponseEntity<String> showConfirmationMessage(@RequestParam String token) {

        String html = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>Confirmation</title>
            </head>
            <body style="font-family: sans-serif; text-align: center; padding-top: 100px;">
                <h2>✅ Your identity has been confirmed</h2>
                <p>You can now return to the app and enter your new password.</p>
                <p>If you did not request a password reset, simply close this page.</p>
            </body>
            </html>
            """;
        return ResponseEntity.ok().body(html);
    }
}
