package com.kapango.domain.model;


import lombok.Builder;

@Builder
public record Integration(Integer id, String prefix, String name, String description, boolean active) {

}
