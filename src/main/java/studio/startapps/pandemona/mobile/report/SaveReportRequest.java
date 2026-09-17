package studio.startapps.pandemona.mobile.report;

public record SaveReportRequest(
    String title,
    String comment,
    String signature
) {}
