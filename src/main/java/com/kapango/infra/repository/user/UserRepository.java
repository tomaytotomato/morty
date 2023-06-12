package com.kapango.infra.repository.user;


import com.kapango.domain.filter.UserFilter;
import com.kapango.domain.model.user.User;
import com.kapango.domain.model.user.UserRole;
import com.kapango.infra.mapper.UserEntityMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final UserDao userDao;
    private final UserRoleDao userRoleDao;
    private final UserEntityMapper mapper;

    @Autowired
    public UserRepository(UserDao userDao, UserRoleDao userRoleDao, UserEntityMapper mapper) {
        this.userDao = userDao;
        this.userRoleDao = userRoleDao;
        this.mapper = mapper;
    }

    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username)
            .map(mapper::fromEntity);
    }

    public List<User> findAll() {
        return userDao.findAll()
            .stream().map(mapper::fromEntity)
            .collect(Collectors.toList());
    }

    public User upsert(User user) {
        var savedUser = userDao.save(mapper.toEntity(user));
        return mapper.fromEntity(savedUser);
    }

    public Optional<User> findById(Integer id) {
        return userDao.findById(id)
            .map(mapper::fromEntity);
    }

    public List<User> findAll(PageRequest pageable) {
        return userDao.findAll(pageable)
            .stream().map(mapper::fromEntity)
            .collect(Collectors.toList());
    }

    public List<User> findAll(PageRequest pageable, UserFilter filter) {
        return userDao.findAll(mapper.toFilter(filter), pageable)
            .stream().map(mapper::fromEntity)
            .collect(Collectors.toList());
    }

    public List<User> findAllByUsername(String username) {
        return userDao.findByUsernameContainingIgnoreCase(username)
            .stream().map(mapper::fromEntity)
            .collect(Collectors.toList());
    }

    public void delete(User user) {
        userDao.deleteById(user.id());
    }

    public List<UserRole> findAllUserRoles() {
        return userRoleDao.findAll()
            .stream().map(mapper::fromEntity)
            .collect(Collectors.toList());
    }
}
