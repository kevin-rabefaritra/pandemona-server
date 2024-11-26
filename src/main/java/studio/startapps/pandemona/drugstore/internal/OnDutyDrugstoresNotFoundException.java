package studio.startapps.pandemona.drugstore.internal;

public class OnDutyDrugstoresNotFoundException extends RuntimeException {

    public OnDutyDrugstoresNotFoundException(long id) {
        super(String.format("On-duty drugstores not found for id %s", id));
    }
}
