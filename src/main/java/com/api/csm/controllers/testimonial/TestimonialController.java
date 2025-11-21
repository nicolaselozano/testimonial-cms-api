package com.api.csm.controllers.testimonial;

import com.api.csm.dto.TestimonialRequestDTO;
import com.api.csm.dto.TestimonialResponseDTO;
import com.api.csm.models.Testimonial;
import com.api.csm.services.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.UUID;

@RestController
@RequestMapping("api/testimonials")
@RequiredArgsConstructor
public class TestimonialController {

    private final TestimonialService testimonialService;

    @PostMapping
    public ResponseEntity<TestimonialResponseDTO> create(
            @RequestBody TestimonialRequestDTO request,
            Authentication authentication
    ){
        UUID userId = UUID.fromString(authentication.getName());

        TestimonialResponseDTO created = testimonialService.createTestimonial(request,userId);

        return ResponseEntity.ok(created);
    }
}
