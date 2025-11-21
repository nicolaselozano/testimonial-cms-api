package com.api.csm.services;

import com.api.csm.dto.TestimonialRequestDTO;
import com.api.csm.dto.TestimonialResponseDTO;
import com.api.csm.mapper.TestimonialMapper;
import com.api.csm.models.*;
import com.api.csm.repository.*;
import com.api.csm.utils.MediaType;
import com.api.csm.utils.TestimonialStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestimonialService {

    private final TestimonialRepository testimonialRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final MediaRepository mediaRepository;
    private final UserRepository userRepository;
    private final TestimonialMapper mapper;

    public TestimonialResponseDTO createTestimonial(TestimonialRequestDTO request, UUID userId){
        User user;
        user= userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        //Obtener categories
        List<Category> categories = categoryRepository.findAllById(
                request.getCategories().stream()
                        .map(UUID::fromString)
                        .collect(Collectors.toList())
        );

        //Obtener tags
        List<Tag> tags = tagRepository.findAllById(
                request.getTags().stream()
                        .map(UUID::fromString)
                        .collect(Collectors.toList())
        );

        Testimonial testimonial = Testimonial.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .status(TestimonialStatus.PENDING)
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .tags(tags)
                .categories(categories)
                .build();

        testimonialRepository.save(testimonial);

        //Guardar medias
        if(request.getMediaUrls()!=null){
            List<Media> mediaList = request.getMediaUrls().stream()
                    .map(url -> Media.builder()
                            .url(url)
                            .type(MediaType.IMAGE)
                            .testimonial(testimonial)
                            .build())
                    .collect(Collectors.toList());

            mediaRepository.saveAll(mediaList);

            testimonial.setMedia(mediaList);
        }

        return mapper.toResponseDTO(testimonial);
    }
}
