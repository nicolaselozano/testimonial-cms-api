package com.api.csm.controllers.category;

import com.api.csm.dto.CategoryRequestDTO;
import com.api.csm.dto.CategoryResponseDTO;
import com.api.csm.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseDTO> getAll(){
        return categoryService.getAll();
    }

    @PostMapping
    public CategoryResponseDTO create(@RequestBody CategoryRequestDTO dto){
        return categoryService.create(dto.getName(),dto.getDescription());
    }
}
