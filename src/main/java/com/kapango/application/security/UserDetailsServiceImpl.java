package com.kapango.application.security;

import com.kapango.application.dto.UserDto;
import com.kapango.application.enums.UserAccountType;
import com.kapango.application.mapper.UserMapper;
import com.kapango.application.usecase.UserUseCase;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserUseCase userService;
    private final UserMapper userMapper;

    @Autowired
    public UserDetailsServiceImpl(UserUseCase userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.findByUsername(username);
        return user.map(existingUser -> new org.springframework.security.core.userdetails.User(existingUser.username(), existingUser.hashedPassword(),
            getAuthorities(userMapper.fromModel(existingUser)))).orElseThrow(() -> new UsernameNotFoundException("No user present with username: " + username));
    }

    private static List<GrantedAuthority> getAuthorities(UserDto user) {
        if (user.getAccountType() == UserAccountType.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_" + UserAccountType.USER),
                new SimpleGrantedAuthority("ROLE_" + UserAccountType.API));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_" + user.getAccountType()));
        }
    }
}
