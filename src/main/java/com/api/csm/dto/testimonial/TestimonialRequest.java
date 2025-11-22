package com.api.csm.dto.testimonial;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data

public class TestimonialRequest {

    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    private String content;

    //Lista de urls de media
    private List<String> mediaUrls;

    //Lista de los ids de categories y tags
    private List<String> categories;

    private List<String> tags;
}
