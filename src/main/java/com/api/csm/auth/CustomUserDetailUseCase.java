package com.api.csm.auth;

import com.api.csm.dto.UserDetailDto;
import com.api.csm.interfaces.UserMapper;
import com.api.csm.models.Role;
import com.api.csm.models.User;
import com.api.csm.repository.RoleRepository;
import com.api.csm.repository.UserRepository;
import com.api.csm.user.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetailUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public User registerUser(String email, String name) throws RuntimeException {
        String randomPassword = UUID.randomUUID().toString();

        log.info(email);
        Role userRole = roleRepository.findByRole(RoleEnum.USER)
                .orElseThrow(() -> new RuntimeException("Default role USER not found"));

        String username = generateUniqueUsername(name);

        User newUser = User.builder()
                .username(username)
                .fullname(name)
                .email(email)
                .password(passwordEncoder.encode(randomPassword))
                .roles(List.of(userRole))
                .build();

        userRepository.save(newUser);

        return newUser;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getRole().name()))
                .toList();

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }

    public User findOrCreateOAuthUser(String email, String name) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> registerUser(email, name));
    }
    @Transactional(readOnly = true)
    public UserDetailDto getUserDetailDtoById(UUID id) {
        log.info("–> service: buscando user por ID {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<String> roles = user.getRoles().stream()
                .map(r -> "ROLE_" + r.getRole().name())
                .toList();
        log.info("–> service: user encontrado {} con roles {}", user.getId(), roles);

        return userMapper.toUserDetailDto(user);
    }

    public User getUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void updatePassword(String email, String newPassword){

        User userExist = userRepository.findByEmail(email).orElseThrow();

        userExist.setPassword(newPassword);

        userRepository.save(userExist);

        log.info("Password actualizado");

    }
    private String generateUniqueUsername(String name) {

        String sanitized = name.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        if (sanitized.isEmpty()) {
            sanitized = "user";
        }

        String username;

        do{
            String randomSuffix = String.valueOf((int)(Math.random() * 10000));
            username = sanitized + randomSuffix;
        }while (userRepository.existsByUsername(username));

        return username;

    }
}
