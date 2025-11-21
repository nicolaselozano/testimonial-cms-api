package com.api.csm.mapper;

import com.api.csm.dto.CategoryResponseDTO;
import com.api.csm.dto.MediaResponseDTO;
import com.api.csm.dto.TagResponseDTO;
import com.api.csm.dto.TestimonialResponseDTO;
import com.api.csm.models.Testimonial;

import org.springframework.stereotype.Component;

@Component
public class TestimonialMapper {

    public TestimonialResponseDTO toResponseDTO(Testimonial t){

        return TestimonialResponseDTO.builder()
                .id(t.getId())
                .title(t.getTitle())
                .content(t.getContent())
                .status(t.getStatus())
                .createdAt(t.getCreatedAt())
                .updatedAt(t.getUpdatedAt())
                .createdById(t.getCreatedBy().getId())
                .createdByName(t.getCreatedBy().getFullname())

                .categories(
                        t.getCategories().stream()
                                .map(c -> CategoryResponseDTO.builder()
                                        .id(c.getId())
                                        .name(c.getName())
                                        .description(c.getDescription())
                                        .build()
                                ).toList()
                )

                .tags(
                        t.getTags().stream()
                                .map(tag -> TagResponseDTO.builder()
                                        .id(tag.getId())
                                        .name(tag.getName())
                                        .build()
                                ).toList()
                )

                .media(
                        t.getMedia().stream()
                                .map(media -> MediaResponseDTO.builder()
                                        .id(media.getId())
                                        .url(media.getUrl())
                                        .type(media.getType())
                                        .build()
                                ).toList()
                )

                .build();
    }
}
