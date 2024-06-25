package studio.startapps.pandemona;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import studio.startapps.pandemona.drugstore.Drugstore;
import studio.startapps.pandemona.drugstore.DrugstoreService;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles({"qa"})
class PandemonaApplicationTests {

	protected final Logger logger = LoggerFactory.getLogger(PandemonaApplicationTests.class);

	@Autowired
	private DrugstoreService drugstoreService;

	@BeforeEach
	public void setUp() {

	}

	@Test
	void checkRepositories() {
		assertNotNull(drugstoreService);
	}

	@Test
	void testGetDrugstoresFirstPage() {
		Sort sort = Sort.by("id");
		Pageable pageable = PageRequest.of(0, 10, sort);

		Page<Drugstore> drugstores = drugstoreService.findAll(pageable);
		assertNotNull(drugstores);
	}

	@Test
	void testGetDrugstores() {
		List<Drugstore> drugstores = drugstoreService.findAll();
		assertNotNull(drugstores);
	}
}
