package com.api.csm.interfaces;

import com.api.csm.models.User;
import com.api.csm.utils.RoleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User create(User user);
    Optional<User> getById(UUID id);
    Optional<User> getByUsername(String username);
    List<User> getAll();
    List<User> getAllByRolePageable(RoleEnum role, Pageable pageable);
    Page<User> getAll(int limit, int page, String sortBy, boolean ascending);
    User update(UUID id, User user);
    void delete(UUID id);
}