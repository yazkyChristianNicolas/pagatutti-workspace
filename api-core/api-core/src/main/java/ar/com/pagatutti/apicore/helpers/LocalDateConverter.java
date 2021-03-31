package ar.com.pagatutti.apicore.helpers;

import org.apache.commons.beanutils.converters.AbstractConverter;

import java.time.LocalDate;

public class LocalDateConverter extends AbstractConverter {

    static final int MAX_LOCAL_DATE_LENGTH = 10;

    @Override
    protected <T> T convertToType(Class<T> type, Object value) {
        if (!(value instanceof String)) {
            throw conversionException(type, value);
        }
        String valueAsString = (String) value;
        if (valueAsString.length() > MAX_LOCAL_DATE_LENGTH) {
            valueAsString = valueAsString.substring(0, MAX_LOCAL_DATE_LENGTH);
        }
        return type.cast(LocalDate.parse(valueAsString));
    }

    @Override
    protected Class<?> getDefaultType() {
        return LocalDate.class;
    }

}
