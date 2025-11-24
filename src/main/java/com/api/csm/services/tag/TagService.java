package com.api.csm.services.tag;

import com.api.csm.dto.tag.TagRequest;
import com.api.csm.dto.tag.TagResponse;
import com.api.csm.models.Tag;
import com.api.csm.repository.tag.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagResponse createTag(TagRequest request) {

        if (tagRepository.existsByNameIgnoreCase(request.name())) {
            throw new IllegalArgumentException("Ya existe un tag con ese nombre");
        }

        Tag tag = new Tag();
        tag.setName(request.name().trim());

        Tag saved = tagRepository.save(tag);
        return mapToResponse(saved);
    }

    public TagResponse updateTag(UUID id, TagRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tag no encontrado"));

        if (!tag.getName().equalsIgnoreCase(request.name())
                && tagRepository.existsByNameIgnoreCase(request.name())) {
            throw new IllegalArgumentException("Ya existe otro tag con ese nombre");
        }

        tag.setName(request.name().trim());
        Tag updated = tagRepository.save(tag);
        return mapToResponse(updated);
    }

    public List<TagResponse> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TagResponse getTagById(UUID id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tag no encontrado"));
        return mapToResponse(tag);
    }

    public void deleteTag(UUID id) {
        if (!tagRepository.existsById(id)) {
            throw new IllegalArgumentException("Tag no encontrado");
        }
        tagRepository.deleteById(id);
    }

    private TagResponse mapToResponse(Tag tag) {
        return new TagResponse(tag.getId(), tag.getName());
    }
}