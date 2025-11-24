package com.api.csm.dto.testimonial;

import com.api.csm.dto.category.CategoryResponse;
import com.api.csm.dto.media.MediaResponse;
import com.api.csm.dto.tag.TagResponse;
import com.api.csm.utils.TestimonialStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TestimonialResponse (
    UUID id,
    String title,
    String content,
    TestimonialStatus status,
    UUID createdById,
    String createdByName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    List<CategoryResponse> categories,
    List<TagResponse> tags,
    List<MediaResponse> media
){}
