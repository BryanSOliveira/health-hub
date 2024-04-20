package br.com.bryan.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

import org.apache.struts2.conversion.TypeConversionException;
import org.apache.struts2.util.StrutsTypeConverter;

@SuppressWarnings("rawtypes")
public class LocalDateConverter extends StrutsTypeConverter {
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        if (values == null || values.length == 0 || values[0].isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(values[0], formatter);
        } catch (DateTimeParseException e) {
            throw new TypeConversionException("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    @Override
    public String convertToString(Map context, Object o) {
        if (o instanceof LocalDate) {
            return ((LocalDate) o).format(formatter);
        }
        return null;
    }
}
