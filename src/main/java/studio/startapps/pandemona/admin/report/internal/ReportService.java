package studio.startapps.pandemona.admin.report.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public Page<Report> findAll(Pageable pageable) {
        return reportRepository.findAll(pageable);
    }
}
