package studio.startapps.pandemona.repository.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import studio.startapps.pandemona.repository.entity.City;

@Converter
public class CityConverter implements AttributeConverter<City, Integer> {
    @Override
    public Integer convertToDatabaseColumn(City city) {
        return city.ordinal();
    }

    @Override
    public City convertToEntityAttribute(Integer integer) {
        return City.values()[integer];
    }
}
