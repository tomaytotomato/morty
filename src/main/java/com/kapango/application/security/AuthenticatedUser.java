package com.kapango.application.security;

import com.kapango.application.dto.UserDto;
import com.kapango.application.mapper.UserMapper;
import com.kapango.application.usecase.UserUseCase;
import com.vaadin.flow.spring.security.AuthenticationContext;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthenticatedUser {

    private final UserUseCase userService;
    private final AuthenticationContext authenticationContext;

    private final UserMapper mapper;

    @Autowired
    public AuthenticatedUser(AuthenticationContext authenticationContext, UserUseCase userService, UserMapper mapper) {
        this.userService = userService;
        this.authenticationContext = authenticationContext;
        this.mapper = mapper;
    }

    @Transactional
    public Optional<UserDto> get() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
            .flatMap(userDetails -> userService.findByUsername(userDetails.getUsername()).map(mapper::fromModel));
    }

    public void logout() {
        authenticationContext.logout();
    }

}
