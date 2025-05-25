package vn.ltdt.SocialNetwork.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @Email
        @NotBlank
        @NotNull
        String email,
        String firstName,
        String lastName,
        @NotBlank
        @NotNull
        String password,
        @NotBlank
        @NotNull
        String phoneNumber
) {
}
