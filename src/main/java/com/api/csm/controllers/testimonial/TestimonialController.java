package com.api.csm.controllers.testimonial;

import com.api.csm.dto.testimonial.TestimonialRequest;
import com.api.csm.dto.testimonial.TestimonialResponse;
import com.api.csm.services.testimonial.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/testimonials")
@RequiredArgsConstructor
public class TestimonialController {

    private final TestimonialService testimonialService;

    @PostMapping
    public ResponseEntity<TestimonialResponse> create(
            @RequestBody TestimonialRequest request,
            Authentication authentication
    ){
        UUID userId = UUID.fromString(authentication.getName());

        TestimonialResponse created = testimonialService.createTestimonial(request,userId);

        return ResponseEntity.ok(created);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TestimonialResponse>> search(
            @RequestParam("query") String query) {
        return ResponseEntity.ok(testimonialService.search(query));
    }
}
