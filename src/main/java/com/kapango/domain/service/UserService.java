package com.kapango.domain.service;

import com.kapango.application.usecase.UserUseCase;
import com.kapango.domain.filter.UserFilter;
import com.kapango.domain.model.user.User;
import com.kapango.domain.model.user.UserRole;
import com.kapango.infra.repository.user.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserUseCase {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<User> findAll(PageRequest pageable, UserFilter filter) {
        return repository.findAll(pageable, filter);
    }

    @Override
    public List<User> findAll(PageRequest pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public User upsert(User user) {
        return repository.upsert(user);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public List<User> findAllByUsername(String username) {
        return repository.findAllByUsername(username);
    }

    @Override
    public List<UserRole> findAllUserRoles() {
        return repository.findAllUserRoles();

    }
}
