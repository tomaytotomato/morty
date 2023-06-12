package com.kapango.application.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {

    private Integer id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private UserResponseDto commenter;
    private String comment;

}
