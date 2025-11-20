package com.api.csm.repository;

import com.api.csm.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    //Para validar duplicados
    Optional<Category> findByName(String name);

}
