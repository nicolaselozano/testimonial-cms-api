package com.api.csm.user;

import com.api.csm.auth.CustomUserDetailUseCase;
import com.api.csm.utils.AesUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/oauth2")
@AllArgsConstructor
public class GoogleAuthController {

    private final AesUtil aesUtil;
    private final CustomUserDetailUseCase customUserDetailUseCase;

    @GetMapping("/success")
    public ResponseEntity<Map<String, String>> success(HttpServletRequest request) {
        return ResponseEntity.ok(Map.of("status", "success"));
    }


}
