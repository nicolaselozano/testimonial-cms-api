package com.api.csm.controllers.testimonial;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/embed")
@RequiredArgsConstructor
public class TestimonialEmbedController {

    @Value("${app.api-url}")
    private String apiUrl;

    @GetMapping(value = "/testimonials.js", produces = "application/javascript")
    public String getEmbedScript() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/embed/testimonials.js");
        String content = new String(resource.getInputStream().readAllBytes());

        return content.replace("__API_URL__", apiUrl);
    }
}
