package com.api.csm.repository.testimonial;

import com.api.csm.models.Testimonial;
import com.api.csm.utils.TestimonialStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TestimonialRepository extends JpaRepository<Testimonial, UUID> {
    List<Testimonial> findByStatus(TestimonialStatus status);
}


