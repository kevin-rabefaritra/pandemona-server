package studio.startapps.pandemona.models;

import jakarta.persistence.*;
import studio.startapps.pandemona.converters.StringListConverter;

import java.util.List;

/**
 * Represents any business
 */
@MappedSuperclass
public abstract class Business extends BaseEntity {

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Convert(converter = StringListConverter.class)
    private List<String> contacts;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Convert(converter = StringListConverter.class)
    @Column(name = "FEATURES")
    private List<String> features;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "CITY")
    private City city;

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
        return contacts;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
