package studio.startapps.pandemona.ondutydrugstores.exception;

public class OnDutyDrugstoresNotFoundException extends Exception {

    public OnDutyDrugstoresNotFoundException(long id) {
        super(String.format("On-duty drugstores not found for id %s", id));
    }
}
