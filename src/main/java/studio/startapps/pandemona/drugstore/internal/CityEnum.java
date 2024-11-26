package studio.startapps.pandemona.drugstore.internal;

public enum CityEnum {

    ANTANANARIVO(0),
    TOAMASINA(1),
    MAHAJANGA(2),
    ANTSIRANANA(3),
    FIANARANTSOA(4),
    TOLIARA(5),
    ANTSIRABE(6),
    NOSYBE(7),
    AMBATONDRAZAKA(8);

    CityEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    private final int value;
}