package com.api.csm.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class TagResponseDTO {
    private UUID id;
    private String name;
}
