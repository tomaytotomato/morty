package com.kapango.domain.model.misc;

import java.time.ZonedDateTime;
import java.util.HashMap;

public record MortyConfig(Integer id, ZonedDateTime created, ZonedDateTime updated, String configGroup, HashMap<String, Object> values) {

}
