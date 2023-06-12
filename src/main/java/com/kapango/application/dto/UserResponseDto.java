package com.kapango.application.dto;

import com.kapango.application.enums.UserAccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private UserAccountType accountType;
    private String team;
    private String position;
}
