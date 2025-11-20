package com.api.csm.repository;

import com.api.csm.models.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TestimonialRepository extends JpaRepository<Testimonial, UUID> {
}
