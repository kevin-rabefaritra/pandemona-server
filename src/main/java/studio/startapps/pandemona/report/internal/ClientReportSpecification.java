package studio.startapps.pandemona.report.internal;

import org.springframework.data.jpa.domain.Specification;

public interface ClientReportSpecification {

    static Specification<ClientReport> withTitleAndComment(final String title, final String comment) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
            criteriaBuilder.equal(root.get("title"), title),
            criteriaBuilder.equal(root.get("comment"), comment)
        );
    }
}
