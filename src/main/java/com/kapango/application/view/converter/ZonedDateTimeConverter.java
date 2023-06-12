package com.kapango.application.view.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class ZonedDateTimeConverter implements Converter<LocalDateTime, ZonedDateTime> {

    private ZoneId zoneId;

    /**
     * Creates a new converter using the given time zone.
     *
     * @param zoneId the time zone to use, not <code>null</code>
     */
    public ZonedDateTimeConverter(ZoneId zoneId) {
        this.zoneId = Objects.requireNonNull(zoneId, "Zone identifier cannot be null");
    }

    public ZonedDateTimeConverter() {
        this.zoneId = ZonedDateTime.now().getZone();
    }

    @Override
    public Result<ZonedDateTime> convertToModel(LocalDateTime localDate, ValueContext context) {
        if (localDate == null) {
            return Result.ok(null);
        }
        return Result.ok(ZonedDateTime.of(localDate, zoneId));
    }

    @Override
    public LocalDateTime convertToPresentation(ZonedDateTime zonedDateTime, ValueContext context) {
        if (zonedDateTime == null) {
            return null;
        }

        return zonedDateTime.toLocalDateTime();
    }
}