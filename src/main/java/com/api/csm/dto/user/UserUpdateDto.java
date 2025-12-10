package com.api.csm.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateDto(
        @NotBlank String username,
        @NotBlank String fullname
) {
}
