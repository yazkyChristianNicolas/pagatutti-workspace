package ar.com.pagatutti.apicore.helpers;

import org.apache.commons.beanutils.converters.AbstractConverter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class LocalDateTimeConverter extends AbstractConverter {

    @Override
    protected <T> T convertToType(Class<T> type, Object value) {
        if (!(value instanceof String)) {
            throw conversionException(type, value);
        }
        String paramValueString = (String) value;
        try {
            return type.cast(LocalDateTime.parse(paramValueString));
        } catch (DateTimeParseException e) {
            //We drop the timezone info from the String:
            return type.cast(ZonedDateTime.parse(paramValueString).toLocalDateTime());
        }
    }

    @Override
    protected Class<?> getDefaultType() {
        return LocalDateTime.class;
    }

}
