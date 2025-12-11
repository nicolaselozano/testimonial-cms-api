package com.api.csm.controllers.role;

import com.api.csm.dto.role.SetUserRoleRequest;
import com.api.csm.interfaces.role.RoleService;
import com.api.csm.models.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Slf4j
@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PatchMapping
    public ResponseEntity<String> setUserRole(@RequestBody SetUserRoleRequest request) {
        return ResponseEntity.ok(roleService.setUserRole(request.getUserId(), request.getRoles()));
    }

    @PostMapping
    public ResponseEntity<Role> create(@RequestBody Role role) {
        return ResponseEntity.ok(roleService.create(role));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getById(@PathVariable UUID id) {
        return roleService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Role>> getAll(
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        return ResponseEntity.ok(roleService.getAll(
                limit,
                page,
                sortBy,
                ascending
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> update(@PathVariable UUID id, @RequestBody Role role) {
        return ResponseEntity.ok(roleService.update(id, role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
