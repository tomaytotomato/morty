package com.kapango.application.mapper;

import com.kapango.application.dto.CommentDto;
import com.kapango.application.dto.TagDto;
import com.kapango.domain.model.misc.Comment;
import com.kapango.domain.model.misc.Tag;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BaseMapper {

    default LocalDateTime fromZonedDateTime(ZonedDateTime zonedDateTime) {
        return Objects.isNull(zonedDateTime) ? null : zonedDateTime.toLocalDateTime();
    }

    default ZonedDateTime fromLocalDateTime(LocalDateTime localDateTime) {
        return Objects.isNull(localDateTime) ? null : ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
    }

    default ZonedDateTime fromLocalDateTime(LocalDateTime localDateTime, ZoneId zoneId) {
        return Objects.isNull(localDateTime) ? null : ZonedDateTime.of(localDateTime,zoneId);
    }

    CommentDto fromModel(Comment comment);

    Comment toModel(CommentDto commentDto);

    TagDto fromModel(Tag tag);

    Tag toModel(TagDto tagDto);
}
