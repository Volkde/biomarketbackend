package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.exception_handling.Response;
import de.aittr.bio_marketplace.service.interfaces.ConfirmationService;
import io.swagger.v3.oas.annotations.Operation;
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
    public Response confirm(@PathVariable String code) {
        try {
            confirmationService.confirmRegistration(code);
            return new Response("Email confirmed successfully! Now you have user rights.");
        } catch (Exception e) {
            return new Response("Error: " + e.getMessage());
        }
    }
}
