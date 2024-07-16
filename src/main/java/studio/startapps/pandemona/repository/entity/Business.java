package studio.startapps.pandemona.repository.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import studio.startapps.pandemona.repository.converter.CityConverter;
import studio.startapps.pandemona.repository.converter.StringListConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents any business
 */
@MappedSuperclass
public abstract class Business extends BaseEntity {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotEmpty
    @Convert(converter = StringListConverter.class)
    private List<String> contacts;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @Convert(converter = StringListConverter.class)
    private List<String> features;

    @NotNull
    @Convert(converter = CityConverter.class)
    private CityEnum city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getContacts() {
        List<String> result = this.contacts != null ? new ArrayList<>(this.contacts) : new ArrayList<>(3);
        while (result.size() < 3) {
            result.add("");
        }
        return result;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public CityEnum getCity() {
        return city;
    }

    public void setCity(CityEnum city) {
        this.city = city;
    }

    public void addContact(String contact) {
        this.contacts.add(contact);
    }
}
