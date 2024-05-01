package studio.startapps.pandemona.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class HealthCenter extends Business {

    public enum Type {
        CLINIC, HOSPITAL
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
