package studio.startapps.pandemona;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.MapFunction;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ResourceUtils;
import studio.startapps.pandemona.models.City;
import studio.startapps.pandemona.models.Drugstore;
import studio.startapps.pandemona.repositories.DrugstoreRepository;
import studio.startapps.pandemona.utils.JsonUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles({"test"})
class PandemonaApplicationTests {

	protected final Logger logger = LoggerFactory.getLogger(PandemonaApplicationTests.class);

	@Autowired
	private DrugstoreRepository drugstoreRepository;

	@BeforeEach
	public void setUp() {
		// Read test-data.json file
		try {
			File testFile = ResourceUtils.getFile("classpath:test-data.json");
			DocumentContext documentContext = JsonPath.parse(testFile);
			List<Map<String, Object>> drugstores = documentContext.read("$.drugstores", List.class);

			drugstores.forEach((entry) -> drugstoreRepository.save(JsonUtils.toDrugstore(entry)));
		}
		catch (IOException e) {
			logger.error("File not found!", e);
		}
	}

	@Test
	void checkRepositories() {
		assertNotNull(drugstoreRepository);
	}

	@Test
	void createDrugstore() {
		Drugstore drugstore = new Drugstore();
		drugstore.setId(1L);
		drugstore.setName("M-Pharmacie");
		drugstore.setAddress("Example of address");
		drugstoreRepository.save(drugstore);

		Drugstore createdDrugstore = this.drugstoreRepository.findFirstById(1L);
		assertThat(createdDrugstore.getId()).isEqualTo(drugstore.getId());
	}
}
