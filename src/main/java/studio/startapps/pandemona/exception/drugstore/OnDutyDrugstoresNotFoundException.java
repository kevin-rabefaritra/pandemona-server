package studio.startapps.pandemona.exception.drugstore;

public class OnDutyDrugstoresNotFoundException extends RuntimeException {

    public OnDutyDrugstoresNotFoundException(long id) {
        super(String.format("On-duty drugstores not found for id %s", id));
    }
}
