package com.kapango.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {

    private Integer id;
    private String name;
    @JsonIgnore
    private LocalDateTime created;
    @JsonIgnore
    private LocalDateTime updated;

}
