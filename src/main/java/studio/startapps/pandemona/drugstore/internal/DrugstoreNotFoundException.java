package studio.startapps.pandemona.drugstore.internal;

public class DrugstoreNotFoundException extends RuntimeException {

    public DrugstoreNotFoundException(long id) {
        super(String.format("Could not find drugstore with id = %s", id));
    }
}
