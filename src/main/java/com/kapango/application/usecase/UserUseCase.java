package com.kapango.application.usecase;

import com.kapango.domain.filter.UserFilter;
import com.kapango.domain.model.user.User;
import com.kapango.domain.model.user.UserRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;

public interface UserUseCase extends CrudUseCase<User> {

    Optional<User> findByUsername(String username);

    List<User> findAll(PageRequest pageable, UserFilter filter);

    List<User> findAll(PageRequest pageable);

    List<User> findAllByUsername(String username);

    List<UserRole> findAllUserRoles();
}
