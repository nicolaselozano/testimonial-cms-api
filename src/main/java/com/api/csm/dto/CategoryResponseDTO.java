package com.api.csm.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CategoryResponseDTO {
    private UUID id;
    private String name;
    private String description;
}
