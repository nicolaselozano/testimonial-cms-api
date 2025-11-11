package com.api.csm.auth;

import com.api.csm.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserUseCase extends DefaultOAuth2UserService {

    private final OAuth2UserHandlerUseCase oAuth2UserHandlerUseCase;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        if (email == null || name == null) {
            throw new OAuth2AuthenticationException("Email or Name attribute is missing from OAuth2 provider");
        }

        User userEntity = oAuth2UserHandlerUseCase.loadOAuthUser(email);

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("userId", userEntity.getId());
        attributes.put("userEntity", userEntity);

        return new DefaultOAuth2User(
                userEntity.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name()))
                        .toList(),
                attributes,
                "email"
        );
    }
}
