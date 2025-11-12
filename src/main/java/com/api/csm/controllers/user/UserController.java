package com.api.csm.controllers.user;

import com.api.csm.auth.CustomUserDetailService;
import com.api.csm.dto.UserDetailDto;
import com.api.csm.interfaces.UserUseCase;
import com.api.csm.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserUseCase userUseCase;
    private final CustomUserDetailService customUserDetailService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.ok(userUseCase.create(user));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailDto> getMeUser(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        UserDetailDto dto = customUserDetailService.getUserDetailDtoById(userId);

        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable UUID id) {
        return userUseCase.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<User>> getAll(
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {

        return ResponseEntity.ok(userUseCase.getAll(
                limit,
                page,
                sortBy,
                ascending
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable UUID id, @RequestBody User user) {
        return ResponseEntity.ok(userUseCase.update(id, user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
