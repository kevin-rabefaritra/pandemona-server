package studio.startapps.pandemona.admin.report.internal;

import org.springframework.data.jpa.domain.Specification;

public interface ReportSpecification {

    static Specification<Report> withTitleAndComment(final String title, final String comment) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
            criteriaBuilder.equal(root.get("title"), title),
            criteriaBuilder.equal(root.get("comment"), comment)
        );
    }
}
