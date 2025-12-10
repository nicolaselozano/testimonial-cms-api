package com.api.csm.controllers.security;

import com.api.csm.auth.CustomUserDetailService;
import com.api.csm.dto.user.UserDetailDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
@Slf4j
public class SecurityController {

    private final CustomUserDetailService customUserDetailService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<String> userRoleControl(){
        return ResponseEntity.ok("USTED ES USUARIO AUTENTICADO");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> adminRoleControl(){
        return ResponseEntity.ok("USTED ES USUARIO ADMINISTRADOR");
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/details")
    public ResponseEntity<UserDetailDto> userDetails(Authentication authentication) {

        UUID userId = UUID.fromString(authentication.getName());
        UserDetailDto dto = customUserDetailService.getUserDetailDtoById(userId);

        return ResponseEntity.ok(dto);
    }
}

