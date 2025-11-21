package com.api.csm.mapper;

import com.api.csm.dto.CategoryResponseDTO;
import com.api.csm.models.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    public CategoryResponseDTO toResponseDTO(Category category){
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public List<CategoryResponseDTO> toResponseDTOList(List<Category> categories){
        return categories.stream().map(this::toResponseDTO).toList();
    }
}
