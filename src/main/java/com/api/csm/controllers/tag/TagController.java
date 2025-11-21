package com.api.csm.controllers.tag;

import com.api.csm.dto.TagRequestDTO;
import com.api.csm.dto.TagResponseDTO;
import com.api.csm.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public List<TagResponseDTO> getAll(){
        return tagService.getAll();
    }

    @PostMapping
    public TagResponseDTO create(@RequestBody TagRequestDTO dto){
        return tagService.create(dto.getName());
    }
}
