package com.api.csm.dto.category;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        String description
) {}
