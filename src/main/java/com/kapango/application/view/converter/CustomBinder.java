package com.kapango.application.view.converter;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.converter.ConverterFactory;
import com.vaadin.flow.data.converter.DefaultConverterFactory;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

public class CustomBinder<BEAN> extends Binder<BEAN> {

    private final CustomConverterFactory converterFactory = new CustomConverterFactory();

    public CustomBinder(Class<BEAN> beanType) {
        super(beanType);
    }

    @Override
    protected ConverterFactory getConverterFactory() {
        return converterFactory;
    }

    /**
     * A custom converter factory that handles String -> Duration conversion
     * and delegates to default converter factory for every other types.
     */
    private static class CustomConverterFactory implements ConverterFactory {

        @SuppressWarnings("unchecked")
        @Override
        public <P, M> Optional<Converter<P, M>> newInstance(
                Class<P> presentationType, Class<M> modelType) {

            if (LocalDateTime.class == presentationType && ZonedDateTime.class == modelType) {
                return Optional.of((Converter<P, M>) new ZonedDateTimeConverter());
            }

            return DefaultConverterFactory.INSTANCE
                    .newInstance(presentationType, modelType);
        }

    }

}
