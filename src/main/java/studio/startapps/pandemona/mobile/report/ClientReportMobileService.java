package studio.startapps.pandemona.mobile.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.admin.report.internal.Report;
import studio.startapps.pandemona.admin.report.internal.ReportRepository;
import studio.startapps.pandemona.admin.report.internal.ReportSpecification;
import studio.startapps.pandemona.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientReportMobileService {

    private final ReportRepository reportRepository;

    void submit(SaveReportRequest request) {
        boolean doesSameReportExist = this.reportRepository.exists(
            ReportSpecification.withTitleAndComment(request.title(), request.comment())
        );

        if (doesSameReportExist) {
            log.warn("[ClientReportMobileService.submit] Report already exists");
            return;
        }

        String expectedSignature = StringUtils.md5("%s-%s".formatted(request.title(), request.comment()));
        if (!request.signature().equals(expectedSignature)) {
            log.error("[ClientReportMobileService.submit] Signature do not match. Expected {}, got {}", expectedSignature, request.signature());
            return;
        }

        Report clientReport = Report.builder()
                .title(request.title())
                .comment(request.comment())
                .build();
        this.reportRepository.save(clientReport);
    }
}
