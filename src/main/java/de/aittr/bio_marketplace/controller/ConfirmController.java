package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.service.interfaces.ConfirmationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/confirm")
public class ConfirmController {

    private final ConfirmationService confirmationService;

    public ConfirmController(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }

    @Operation(summary = "Confirm registration by code")
    @GetMapping("/{code}")
    public ResponseEntity<String> confirm(@PathVariable String code) {
        try {
            confirmationService.confirmRegistration(code);

            String html = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>Email Confirmed</title>
            </head>
            <body style="font-family: sans-serif; text-align: center; padding-top: 100px; background-color: #f9f9f9;">
                <div style="max-width: 600px; margin: auto; padding: 30px; background: #fff; border-radius: 10px; box-shadow: 0 4px 12px rgba(0,0,0,0.1);">
                    <h2 style="color: #28a745;">✅ Email confirmed successfully!</h2>
                    <p style="font-size: 16px; color: #444;">Now you have user rights. You can return to the application.</p>
                    <a href="yourapp://open" style="display: inline-block; margin-top: 20px; padding: 12px 24px; background-color: #28a745; color: white; text-decoration: none; border-radius: 5px;">
                        Back to App
                    </a>
                </div>
            </body>
            </html>
        """;

            return ResponseEntity.ok().body(html);

        } catch (Exception e) {
            String errorHtml = String.format("""
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>Confirmation Error</title>
            </head>
            <body style="font-family: sans-serif; text-align: center; padding-top: 100px; background-color: #fff0f0;">
                <h2 style="color: red;">❌ Confirmation failed</h2>
                <p>Error: %s</p>
            </body>
            </html>
            """, e.getMessage());

            return ResponseEntity.badRequest().body(errorHtml);
        }
    }
}
