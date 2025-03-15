package studio.startapps.pandemona.report.mobile;

public record SaveReportRequest(
    String title,
    String comment,
    String signature
) {}
