package com.api.csm.repository;

import com.api.csm.models.User;
import com.api.csm.utils.RoleEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findByRoles_Role(RoleEnum role, Pageable pageable);
    Boolean existsByUsername(String username);
}
