package studio.startapps.pandemona.drugstore.internal;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class DrugstoreSpecification implements Specification<Drugstore> {

    private final Map<String, String> filters;

    @Override
    public Predicate toPredicate(Root<Drugstore> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        filters.forEach((key, value) -> {
            if (value.isBlank()) {
                return;
            }

            switch (key) {
                case "keyword":
                    String keyword = value.toLowerCase().trim();
                    predicateList.add(
                        criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), String.format("%%%s%%", keyword)),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), String.format("%%%s%%", keyword)),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("contacts")), String.format("%%%s%%", keyword))
                        )
                    );
                    break;
                case "city":
                    predicateList.add(criteriaBuilder.equal(root.get("city"), value.toUpperCase()));
                    break;
                default:
                    log.warn("[DrugstoreSpecification] Skipped filter: {}", key);
                    break;
            }
        });
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
