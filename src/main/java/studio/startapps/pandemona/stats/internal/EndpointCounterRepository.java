package studio.startapps.pandemona.stats.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EndpointCounterRepository extends JpaRepository<EndpointCounter, Long> {

    @Query("SELECT ec FROM EndpointCounter ec WHERE ec.requestEndpoint = :endpoint AND ec.requestDate = :date AND ec.version = :version LIMIT 1")
    Optional<EndpointCounter> findByEndpointDateAndVersion(@Param("endpoint") String endpoint, @Param("date") LocalDate date, @Param("version") String version);

    @Query("SELECT ec FROM EndpointCounter ec WHERE ec.requestDate = :date")
    List<EndpointCounter> findAllByDate(@Param("date") LocalDate date);
}
