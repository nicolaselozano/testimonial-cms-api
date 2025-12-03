package com.api.csm.controllers.testimonial;

import com.api.csm.dto.testimonial.ModerateTestimonialRequest;
import com.api.csm.dto.testimonial.TestimonialRequest;
import com.api.csm.dto.testimonial.TestimonialResponse;
import com.api.csm.services.testimonial.TestimonialService;
import com.api.csm.utils.TestimonialStatus;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PatchMapping("/{id}/moderate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TestimonialResponse> moderate(
        @PathVariable UUID id,
        @Valid @RequestBody ModerateTestimonialRequest request){

        TestimonialResponse response = testimonialService.moderate(id,request.status());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PermitAll
    public ResponseEntity<List<TestimonialResponse>> getApproved(
            @RequestParam(required = false)TestimonialStatus status) {

        if (status == null) status = TestimonialStatus.APPROVED;
        return ResponseEntity.ok(testimonialService.findByStatus(status));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TestimonialResponse>> search(
            @RequestParam("query") String query) {

        return ResponseEntity.ok(testimonialService.search(query));
    }
}
