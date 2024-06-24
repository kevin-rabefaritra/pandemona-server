package studio.startapps.pandemona;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ResourceUtils;
import studio.startapps.pandemona.drugstore.Drugstore;
import studio.startapps.pandemona.drugstore.DrugstoreService;
import studio.startapps.pandemona.ondutydrugstores.OnDutyDrugstores;
import studio.startapps.pandemona.ondutydrugstores.OnDutyDrugstoresRepository;
import studio.startapps.pandemona.utils.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles({"test"})
class PandemonaApplicationTests {

	protected final Logger logger = LoggerFactory.getLogger(PandemonaApplicationTests.class);

	@Autowired
	private DrugstoreService drugstoreService;

	@Autowired
	private OnDutyDrugstoresRepository onDutyDrugstoresRepository;

	@BeforeEach
	public void setUp() {
		// Read test-data.json file
		try {
			File testFile = ResourceUtils.getFile("classpath:test-data.json");
			DocumentContext documentContext = JsonPath.parse(testFile);
			List<Map<String, Object>> drugstores = documentContext.read("$.drugstores", List.class);
			List<Map<String, Object>> onDutyDrugstores = documentContext.read("$.ondutyDrugstores", List.class);

			drugstores.forEach((entry) -> {
				// Save drugstore
				drugstoreService.save(JsonUtils.toDrugstore(entry));
			});

			onDutyDrugstores.forEach((entry) -> {
				OnDutyDrugstores dutyDrugstores = JsonUtils.toOnDutyDrugstores(entry);
				Set<Drugstore> drugstoreList = new HashSet<>(drugstoreService.findByIdIn(dutyDrugstores.drugstoreIds));
				dutyDrugstores.setDrugstores(drugstoreList);

				// Save on-duty drugstores
				onDutyDrugstoresRepository.save(dutyDrugstores);
			});
		}
		catch (IOException e) {
			logger.error("File not found!", e);
		}
	}

	@Test
	void checkRepositories() {
		assertNotNull(drugstoreService);
		assertNotNull(onDutyDrugstoresRepository);
	}

	@Test
	@Transactional
	void testGetOnDutyDrugstores() {
		Optional<OnDutyDrugstores> onDutyDrugstores = onDutyDrugstoresRepository.findById(1L);
		assertThat(onDutyDrugstores.isPresent()).isEqualTo(true);

		OnDutyDrugstores onDutyDrugstores1 = onDutyDrugstores.get();
		Set<Drugstore> drugstores = onDutyDrugstores1.getDrugstores();
		for (Drugstore drugstore : drugstores) {
			assertNotNull(drugstore);
			assertNotNull(drugstore.getName());
		}
	}

	@Test
	void testGetOnDutyDrugstoresByDate() {
		LocalDate currentDate = LocalDate.of(2024, Month.JUNE, 6);
		List<OnDutyDrugstores> onDutyDrugstoresList = onDutyDrugstoresRepository.findBetweenStartDateAndEndDate(currentDate.toString());
		assertThat(onDutyDrugstoresList.size()).isGreaterThan(0);

		for (OnDutyDrugstores onDutyDrugstores : onDutyDrugstoresList) {
			assertNotNull(onDutyDrugstores);
			LocalDate startDate = onDutyDrugstores.getStartDate();
			LocalDate endDate = onDutyDrugstores.getEndDate();

			assertThat(currentDate).isAfterOrEqualTo(startDate);
			assertThat(currentDate).isBeforeOrEqualTo(endDate);
		}
	}
}
