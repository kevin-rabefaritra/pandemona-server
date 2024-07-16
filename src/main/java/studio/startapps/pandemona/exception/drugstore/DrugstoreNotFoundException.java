package studio.startapps.pandemona.exception.drugstore;

public class DrugstoreNotFoundException extends RuntimeException {

    public DrugstoreNotFoundException(long id) {
        super(String.format("Could not find drugstore with id = %s", id));
    }
}
