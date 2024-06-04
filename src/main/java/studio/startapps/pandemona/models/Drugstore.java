package studio.startapps.pandemona.models;

import jakarta.persistence.Entity;

import java.util.List;

/**
 * Drugstores
 */
@Entity
public class Drugstore extends Business {

    public Drugstore(long id, String name, String address, List<String> contacts, double latitude, double longitude,
                     List<String> features, City city) {
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setContacts(contacts);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setFeatures(features);
        this.setCity(city);
    }

    public Drugstore() {

    }
}
