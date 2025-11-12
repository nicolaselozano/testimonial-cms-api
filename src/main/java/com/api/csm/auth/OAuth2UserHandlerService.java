package com.api.csm.auth;

import com.api.csm.models.User;
import com.api.csm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserHandlerService {

    private final UserRepository userRepository;

    public User loadOAuthUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow();
    }
}