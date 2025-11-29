package com.api.csm.services.testimonial;

import com.api.csm.dto.category.CategoryResponse;
import com.api.csm.dto.media.MediaResponse;
import com.api.csm.dto.tag.TagResponse;
import com.api.csm.dto.testimonial.TestimonialRequest;
import com.api.csm.dto.testimonial.TestimonialResponse;
import com.api.csm.models.*;
import com.api.csm.repository.*;
import com.api.csm.repository.category.CategoryRepository;
import com.api.csm.repository.media.MediaRepository;
import com.api.csm.repository.tag.TagRepository;
import com.api.csm.repository.testimonial.TestimonialRepository;
import com.api.csm.utils.MediaType;
import com.api.csm.utils.TestimonialStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public TestimonialResponse createTestimonial(TestimonialRequest request, UUID userId){

        User user = userRepository.findById(userId)
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


        List<Media> mediaList = new ArrayList<>();

        //Guardar imágenes
        if(request.getImageUrls()!=null){
            mediaList.addAll(
                request.getImageUrls().stream()
                    .map(url -> Media.builder()
                            .url(url)
                            .type(MediaType.IMAGE)
                            .testimonial(testimonial)
                            .build())
                    .toList());
        }

        //Guardar videos
        if(request.getVideoUrls()!=null){
            mediaList.addAll(
                    request.getVideoUrls().stream()
                            .map(url -> Media.builder()
                                    .url(url)
                                    .type(MediaType.VIDEO)
                                    .testimonial(testimonial)
                                    .build())
                            .toList());
        }

        if(!mediaList.isEmpty()){
            testimonial.setMedia(mediaList);
        }

        Testimonial saved = testimonialRepository.save(testimonial);
        return mapToResponse(saved);
    }

    private TestimonialResponse mapToResponse(Testimonial t){
        List<Media> images = t.getMedia().stream()
                .filter(m -> m.getType() == MediaType.IMAGE)
                .toList();

        List<Media> videos = t.getMedia().stream()
                .filter(m -> m.getType() == MediaType.VIDEO)
                .toList();

        return new TestimonialResponse(
                t.getId(),
                t.getTitle(),
                t.getContent(),
                t.getStatus(),
                t.getCreatedBy().getId(),
                t.getCreatedBy().getFullname(),
                t.getCreatedAt(),
                t.getUpdatedAt(),
                t.getCategories().stream()
                        .map(cat -> new CategoryResponse(cat.getId(), cat.getName(), cat.getDescription()))
                        .toList(),
                t.getTags().stream()
                        .map(tag -> new TagResponse(tag.getId(),tag.getName()))
                        .toList(),
                images.stream()
                                .map(m -> new MediaResponse(m.getId(),m.getUrl(),m.getType()))
                        .toList(),
                videos.stream()
                                .map(m -> new MediaResponse(m.getId(),m.getUrl(),m.getType()))
                        .toList()
        );
    }

    @Transactional
    public TestimonialResponse moderate(UUID id,TestimonialStatus newStatus){
        Testimonial t = testimonialRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Testimonio no encontrado."));

        if(t.getStatus() == TestimonialStatus.APPROVED)
            throw new IllegalArgumentException("No se puede moderar un testimonio Aprobado.");

        t.setStatus(newStatus);
        t.setUpdatedAt(LocalDateTime.now());
        Testimonial saved = testimonialRepository.save(t);
        return mapToResponse(saved);
    }

    // findByStatus para filtrar las APPROVED
    public List<TestimonialResponse> findByStatus(TestimonialStatus status){
        return testimonialRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}
