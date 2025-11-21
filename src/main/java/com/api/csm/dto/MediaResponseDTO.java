package com.api.csm.dto;

import com.api.csm.utils.MediaType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import org.checkerframework.checker.signature.qual.BinaryName;

import java.util.UUID;

@Data
@Builder
public class MediaResponseDTO {
    private UUID id;
    private String url;
    private MediaType type;
}
