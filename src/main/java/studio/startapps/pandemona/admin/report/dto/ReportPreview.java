package studio.startapps.pandemona.admin.report.dto;

import studio.startapps.pandemona.admin.report.internal.Report;

import java.time.LocalDateTime;

public record ReportPreview(
        LocalDateTime createdAt,
        String title,
        String content
) {
    public ReportPreview(Report report) {
        this(report.getCreatedAt(), report.getTitle(), report.getComment());
    }
}
