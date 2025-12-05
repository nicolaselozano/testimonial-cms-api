package com.api.csm.dto.testimonial;

public record TestimonialStatsResponse(
        long total,
        long approved,
        long pending,
        long rejected
) {}
