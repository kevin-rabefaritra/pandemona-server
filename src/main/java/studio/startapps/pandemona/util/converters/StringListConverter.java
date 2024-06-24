package studio.startapps.pandemona.util.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String DELIMITER = ";";

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        return strings != null && !strings.isEmpty() ? String.join(DELIMITER, strings) : null;
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        return s != null && !s.isEmpty() ? List.of(s.split(DELIMITER)) : null;
    }
}
