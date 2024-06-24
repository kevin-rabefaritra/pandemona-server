package studio.startapps.pandemona.utils;

import net.minidev.json.JSONArray;
import studio.startapps.pandemona.util.models.City;
import studio.startapps.pandemona.drugstore.Drugstore;
import studio.startapps.pandemona.ondutydrugstores.OnDutyDrugstores;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    /**
     * Converts a Map<String, Object> of a drugstore to an entity object
     * @param map
     * @return
     */
    public static Drugstore toDrugstore(Map<String, Object> map) {
        List<String> contacts = ((JSONArray) map.get("contacts")).stream().map(v -> (String) v).toList();
        List<String> features = ((JSONArray) map.get("features")).stream().map(v -> (String) v).toList();

        return new Drugstore(
            Long.parseLong(map.get("id").toString()),
            (String) map.get("name"),
            (String) map.get("address"),
            contacts,
            (double) map.get("latitude"),
            (double) map.get("longitude"),
            features,
            City.values()[(int) map.get("city")]
        );
    }

    public static OnDutyDrugstores toOnDutyDrugstores(Map<String, Object> map) {
        List<Long> drugstoreIds = ((JSONArray) map.get("drugstores")).stream().map((item) -> Long.parseLong(item.toString())).toList();

        return new OnDutyDrugstores(
            Long.parseLong(map.get("id").toString()),
            LocalDate.parse((String) map.get("startDate")),
            LocalDate.parse((String) map.get("endDate")),
            drugstoreIds
        );
    }
}
