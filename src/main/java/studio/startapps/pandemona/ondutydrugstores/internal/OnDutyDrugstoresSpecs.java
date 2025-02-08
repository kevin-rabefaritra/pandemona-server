package studio.startapps.pandemona.ondutydrugstores.internal;

import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import studio.startapps.pandemona.util.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class OnDutyDrugstoresSpecs {
    public static Specification<OnDutyDrugstores> withParams(final Map<String, String> params) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> specificationList = new ArrayList<>();

            params.forEach((key, value) -> {
                switch (key) {
                    case "start":
                        Predicate startPredicate = criteriaBuilder.equal(root.get("startDate"), DateUtils.parseDate(value));
                        specificationList.add(startPredicate);
                        break;
                    case "end":
                        Predicate endPredicate = criteriaBuilder.equal(root.get("endDate"), DateUtils.parseDate(value));
                        specificationList.add(endPredicate);
                        break;
                    default:
                        log.info("[OnDutyDrugstoresSpecs] Ignored parameter {}", key);
                        break;
                }
            });
            return criteriaBuilder.and(specificationList.toArray(new Predicate[0]));
        };
    }
}
