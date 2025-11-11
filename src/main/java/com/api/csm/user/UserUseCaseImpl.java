package com.api.csm.user;

import com.api.csm.interfaces.UserUseCase;
import com.api.csm.models.User;
import com.api.csm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {
    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getById(UUID id) {
        return userRepository.findById(id);
    }
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Page<User> getAll(int limit, int page, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, limit, sort);
        return userRepository.findAll(pageable);
    }

    public List<User> getAllByRolePageable(RoleEnum role, Pageable pageable) {
        return userRepository.findByRoles_Role(role,pageable);
    }

    public User update(UUID id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
