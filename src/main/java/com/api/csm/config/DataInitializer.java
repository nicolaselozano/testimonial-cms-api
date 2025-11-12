package com.api.csm.config;

import com.api.csm.models.Role;
import com.api.csm.repository.RoleRepository;
import com.api.csm.utils.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) {

        Arrays.stream(RoleEnum.values()).forEach(roleEnum -> {
            roleRepository.findByRole(roleEnum).orElseGet(() -> {
                Role role = Role.builder()
                        .role(roleEnum)
                        .description("Rol " + roleEnum.name())
                        .build();
                return roleRepository.save(role);
            });
        });
    }
}
