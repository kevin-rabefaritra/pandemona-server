package studio.startapps.pandemona.report.mobile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.report.internal.ClientReport;
import studio.startapps.pandemona.report.internal.ClientReportRepository;
import studio.startapps.pandemona.report.internal.ClientReportSpecification;
import studio.startapps.pandemona.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientReportMobileService {

    private final ClientReportRepository reportRepository;

    void submit(SaveReportRequest request) {
        boolean doesSameReportExist = this.reportRepository.exists(
            ClientReportSpecification.withTitleAndComment(request.title(), request.comment())
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

        ClientReport clientReport = ClientReport.builder()
                .title(request.title())
                .comment(request.comment())
                .build();
        this.reportRepository.save(clientReport);
    }
}
