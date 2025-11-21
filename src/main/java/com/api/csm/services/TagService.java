package com.api.csm.services;

import com.api.csm.dto.TagResponseDTO;
import com.api.csm.mapper.TagMapper;
import com.api.csm.models.Tag;
import com.api.csm.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public List<TagResponseDTO> getAll(){
        return tagMapper.toRespondeDTOList(tagRepository.findAll());
    }

    public TagResponseDTO getById(UUID id){
        return tagRepository.findById(id)
                .map(tagMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Tag no encontrado."));
    }

    public TagResponseDTO create(String name){
        if(tagRepository.findByName(name).isPresent()){
            throw new IllegalArgumentException("Tag existente.");
        }

        Tag tag = Tag.builder().name(name).build();
        return tagMapper.toResponseDTO(tagRepository.save(tag));
    }

    public void delete(UUID id){
        tagRepository.deleteById(id);
    }
}
