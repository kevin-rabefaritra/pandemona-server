package studio.startapps.pandemona.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class EmergencyNumber extends Business {

    public enum Type {
        POLICE, AMBULANCE, FIREFIGHTERS, OTHER
    }

    @Enumerated(EnumType.ORDINAL)
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
