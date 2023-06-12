package com.kapango.application.view.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class LocalDateTimeToStringConverter implements Converter<String, LocalDateTime> {

    private ZoneId zoneId;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Creates a new converter using the given time zone.
     *
     * @param zoneId the time zone to use, not <code>null</code>
     */
    public LocalDateTimeToStringConverter(ZoneId zoneId) {
        this.zoneId = Objects.requireNonNull(zoneId, "Zone identifier cannot be null");
    }

    public LocalDateTimeToStringConverter() {
        zoneId = ZoneId.systemDefault();
    }

    @Override
    public Result<LocalDateTime> convertToModel(String value, ValueContext context) {
        if (Objects.isNull(value) || StringUtils.isEmpty(value)) {
            return Result.ok(null);
        }
        return Result.ok(LocalDateTime.parse(value, formatter));
    }

    @Override
    public String convertToPresentation(LocalDateTime value, ValueContext context) {
        return value.format(formatter);
    }
}