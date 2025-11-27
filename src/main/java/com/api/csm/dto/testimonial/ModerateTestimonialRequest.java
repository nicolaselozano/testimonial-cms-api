package com.api.csm.dto.testimonial;

import com.api.csm.utils.TestimonialStatus;
import jakarta.validation.constraints.NotNull;

public record ModerateTestimonialRequest(
        @NotNull TestimonialStatus status
        ){}
