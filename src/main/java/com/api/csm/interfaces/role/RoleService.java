package com.api.csm.interfaces.role;

import com.api.csm.models.Role;
import com.api.csm.utils.RoleEnum;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleService {
    Role create(Role role) throws RuntimeException;
    Optional<Role> getById(UUID id) throws RuntimeException;
    Page<Role> getAll(int limit, int page, String sortBy, boolean ascending) throws RuntimeException;
    Role update(UUID id, Role role) throws RuntimeException;
    void delete(UUID id) throws RuntimeException;
    String setUserRole(UUID userId, List<RoleEnum> roles) throws RuntimeException;
}