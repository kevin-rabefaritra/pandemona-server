package studio.startapps.pandemona.drugstore.internal;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Optional;

@Converter
public class CityConverter implements AttributeConverter<CityEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CityEnum city) {
        return city.getValue();
    }

    @Override
    public CityEnum convertToEntityAttribute(Integer value) {
        Optional<CityEnum> cityEnum = Arrays.stream(CityEnum.values()).filter(item -> item.getValue() == value).findFirst();
        return cityEnum.orElse(null); // should not return null
    }
}
