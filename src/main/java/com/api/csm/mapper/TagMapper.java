package com.api.csm.mapper;

import com.api.csm.dto.TagResponseDTO;
import com.api.csm.models.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TagMapper {

    public TagResponseDTO toResponseDTO(Tag tag){
        return TagResponseDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    public List<TagResponseDTO> toRespondeDTOList(List<Tag> tags){
        return tags.stream().map(this::toResponseDTO).toList();
    }
}
