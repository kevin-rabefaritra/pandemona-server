package studio.startapps.pandemona.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Drugstores
 */
@Entity
@Table(name = "drugstore")
public class Drugstore extends Business {

    public Drugstore(long id, String name, String address, List<String> contacts, double latitude, double longitude,
                     List<String> features, CityEnum city) {
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setContacts(contacts);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setFeatures(features);
        this.setCity(city);
    }

    public Drugstore(String name, String address, List<String> contacts, double latitude, double longitude,
                     List<String> features, CityEnum city) {
        this(0, name, address, contacts, latitude, longitude, features, city);
    }

    public Drugstore() {
        this.setContacts(new ArrayList<>(3));
    }

    @Override
    public boolean equals(Object b) {
        if (b == null) {
            return false;
        }
        if (!(b instanceof Drugstore b_)) {
            return false;
        }
        return Objects.equals(this.getId(), b_.getId());
    }

    @Override
    public int hashCode() {
        return Long.valueOf(this.getId()).hashCode();
    }
}
