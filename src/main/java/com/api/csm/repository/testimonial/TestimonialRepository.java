package com.api.csm.repository.testimonial;

import com.api.csm.models.Testimonial;
import com.api.csm.utils.TestimonialStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TestimonialRepository extends JpaRepository<Testimonial, UUID> {
    @Query("""
           SELECT DISTINCT t
           FROM Testimonial t
           LEFT JOIN t.categories c
           LEFT JOIN t.tags g
           WHERE t.status = :status
             AND (
                  LOWER(t.title)   LIKE LOWER(CONCAT('%', :query, '%'))
               OR LOWER(t.content) LIKE LOWER(CONCAT('%', :query, '%'))
               OR LOWER(c.name)    LIKE LOWER(CONCAT('%', :query, '%'))
               OR LOWER(g.name)    LIKE LOWER(CONCAT('%', :query, '%'))
             )
           """)
    List<Testimonial> searchTestimonialsByQuery( @Param("status") TestimonialStatus status, @Param("query") String query);
}
