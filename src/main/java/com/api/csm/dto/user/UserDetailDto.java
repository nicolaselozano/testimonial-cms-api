package com.api.csm.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UserDetailDto(
        @NotBlank UUID id,
        @NotBlank @Email String email,
        @NotBlank String fullname
) {
}
