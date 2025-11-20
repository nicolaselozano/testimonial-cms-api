package com.api.csm.controllers.testimonial;

import com.api.csm.dto.CreateTestimonialRequest;
import com.api.csm.models.Testimonial;
import com.api.csm.services.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("api/testimonials")
@RequiredArgsConstructor
public class TestimonialController {

    private final TestimonialService testimonialService;

    @PostMapping
    public ResponseEntity<Testimonial> create(
            @RequestBody CreateTestimonialRequest request,
            Principal principal
    ){
        UUID userId = UUID.fromString(principal.getName());

        Testimonial created = testimonialService.createTestimonial(request,userId);

        return ResponseEntity.ok(created);
    }
}
