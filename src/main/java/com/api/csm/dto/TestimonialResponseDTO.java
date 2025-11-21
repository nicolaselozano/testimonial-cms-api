package com.api.csm.dto;

import com.api.csm.models.Category;
import com.api.csm.models.User;
import com.api.csm.utils.TestimonialStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class TestimonialResponseDTO {

    private UUID id;
    private String title;
    private String content;
    private TestimonialStatus status;

    private UUID createdById;
    private String createdByName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<CategoryResponseDTO> categories;
    private List<TagResponseDTO> tags;
    private List<MediaResponseDTO> media;
}
