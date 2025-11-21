package com.api.csm.controllers.tag;

import com.api.csm.dto.tag.TagRequest;
import com.api.csm.dto.tag.TagResponse;
import com.api.csm.services.tag.TagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // POST /api/tags
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TagResponse> createTag(
            @Valid @RequestBody TagRequest request) {

        TagResponse created = tagService.createTag(request);

        return ResponseEntity
                .created(URI.create("/api/tags/" + created.id()))
                .body(created);
    }

    // PUT /api/tags/{id}
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TagResponse> updateTag(
            @PathVariable Long id,
            @Valid @RequestBody TagRequest request) {

        TagResponse updated = tagService.updateTag(id, request);
        return ResponseEntity.ok(updated);
    }

    // GET /api/tags
    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    // GET /api/tags/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getTagById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTagById(id));
    }

    // DELETE /api/tags/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
