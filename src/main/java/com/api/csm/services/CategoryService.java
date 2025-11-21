package com.api.csm.services;

import com.api.csm.dto.CategoryResponseDTO;
import com.api.csm.mapper.CategoryMapper;
import com.api.csm.models.Category;
import com.api.csm.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponseDTO> getAll(){
        return categoryMapper.toResponseDTOList(categoryRepository.findAll());
    }

    public CategoryResponseDTO getById(UUID id){
        return categoryRepository.findById(id)
                .map(categoryMapper::toResponseDTO)
                .orElseThrow(()-> new RuntimeException("Categoria no encontrada."));
    }

    public CategoryResponseDTO create(String name, String description) {
        if (categoryRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Categoria existente.");
        }
        Category category = Category.builder()
                .name(name)
                .description(description)
                .build();
        return categoryMapper.toResponseDTO(categoryRepository.save(category));
    }

    public void delete(UUID id){
        categoryRepository.deleteById(id);
    }
}
