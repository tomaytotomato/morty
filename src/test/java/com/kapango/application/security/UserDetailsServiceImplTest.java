package com.kapango.application.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kapango.application.mapper.BaseMapperImpl;
import com.kapango.application.mapper.UserMapper;
import com.kapango.application.usecase.UserUseCase;
import com.kapango.domain.model.user.User;
import com.kapango.domain.model.user.UserAccountType;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserUseCase userService;

    private UserDetailsServiceImpl userDetailsService;

    private UserMapper userMapper;

    @BeforeEach
    void setup() {
        userMapper = UserMapper.INSTANCE;
        ReflectionTestUtils.setField(userMapper, "baseMapper", new BaseMapperImpl());
        userDetailsService = new UserDetailsServiceImpl(userService, userMapper);
    }

    @Test
    void loadUserByUsername_whenBasicUserExists_thenReturnUserDetailsWithCorrectAuthorities() {
        // Given
        var username = "john_doe";
        var hashedPassword = "hashbrown";
        var existingUser = User.builder()
            .username(username)
            .hashedPassword(hashedPassword)
            .accountType(UserAccountType.USER)
            .build();

        when(userService.findByUsername(username)).thenReturn(Optional.of(existingUser));

        // When
        var userDetails = userDetailsService.loadUserByUsername(username);

        // Then
        assertThat(userDetails)
            .isNotNull()
            .extracting(UserDetails::getUsername, UserDetails::getPassword, UserDetails::getAuthorities)
            .containsExactly(username, hashedPassword, Set.of(new SimpleGrantedAuthority("ROLE_" + UserAccountType.USER)));

        verify(userService, times(1)).findByUsername(username);
    }

    @Test
    void loadUserByUsername_whenAdminUserExists_thenReturnUserDetailsWithAllAuthorities() {
        // Given
        var username = "paul_atreides";
        var hashedPassword = "muadib";
        var existingUser = User.builder()
            .username(username)
            .hashedPassword(hashedPassword)
            .accountType(UserAccountType.ADMIN)
            .build();

        when(userService.findByUsername(username)).thenReturn(Optional.of(existingUser));

        // When
        var userDetails = userDetailsService.loadUserByUsername(username);

        // Then
        assertThat(userDetails)
            .isNotNull()
            .extracting(UserDetails::getUsername, UserDetails::getPassword, UserDetails::getAuthorities)
            .containsExactly(username, hashedPassword, Set.of(new SimpleGrantedAuthority("ROLE_" + UserAccountType.ADMIN),
                new SimpleGrantedAuthority("ROLE_" + UserAccountType.USER),
                new SimpleGrantedAuthority("ROLE_" + UserAccountType.API)));

        verify(userService, times(1)).findByUsername(username);
    }

    @Test
    void loadUserByUsername_whenUserDoesntExist_thenThrowException() {
        // Arrange
        var username = "non_existing_user";
        when(userService.findByUsername(username)).thenReturn(Optional.empty());

        // Act and Assert
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(username))
            .isInstanceOf(UsernameNotFoundException.class)
            .hasMessage("No user present with username: " + username);

        verify(userService, times(1)).findByUsername(username);
    }
}