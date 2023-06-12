package com.kapango.application.dto;

import com.kapango.application.enums.UserAccountType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String username;
    private String firstName;
    private String lastName;
    private String hashedPassword;
    private UserAccountType accountType;
    private UserRoleDto role;
    private String team;
    private String position;
    private byte[] profilePicture;
    private boolean important;

    public String getName() {
        return firstName + " " + lastName;
    }

}
