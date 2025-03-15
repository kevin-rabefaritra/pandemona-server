package studio.startapps.pandemona.report.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientReportRepository extends JpaRepository<ClientReport, Long>, JpaSpecificationExecutor<ClientReport> {

}
