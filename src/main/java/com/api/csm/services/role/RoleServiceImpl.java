package com.api.csm.services.role;


import com.api.csm.interfaces.role.RoleService;
import com.api.csm.models.Role;
import com.api.csm.models.User;
import com.api.csm.repository.RoleRepository;
import com.api.csm.repository.UserRepository;
import com.api.csm.utils.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public String setUserRole(UUID userId, List<RoleEnum> roles) {

        AtomicBoolean userRoleExist = new AtomicBoolean(false);

        List<Role> rolesExist = roles.stream()
                .map(roleEnum -> {
                    if(roleEnum == RoleEnum.USER) userRoleExist.set(true);
                    return roleRepository.findByRole(roleEnum)
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleEnum));})
                .collect(Collectors.toList());

        if (!userRoleExist.get()) {
            rolesExist.add(roleRepository.findByRole(RoleEnum.USER).orElseThrow());
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRoles(rolesExist);

        userRepository.save(user);

        return "Roles asignados exitosamente";
    }

    public Role create(Role role) throws RuntimeException {
        return roleRepository.save(role);
    }

    public Optional<Role> getById(UUID id) throws RuntimeException {
        return roleRepository.findById(id);
    }

    public Page<Role> getAll(int limit, int page, String sortBy, boolean ascending) throws RuntimeException {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, limit, sort);
        return roleRepository.findAll(pageable);
    }

    public Role update(UUID id, Role role) throws RuntimeException {
        role.setId(id);
        return roleRepository.save(role);
    }

    public void delete(UUID id) throws RuntimeException {
        roleRepository.deleteById(id);
    }
}