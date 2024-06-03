package studio.startapps.pandemona;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import studio.startapps.pandemona.models.Drugstore;
import studio.startapps.pandemona.repositories.DrugstoreRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles({"test"})
class PandemonaApplicationTests {

	@Autowired
	private DrugstoreRepository drugstoreRepository;

	@BeforeEach
	public void setUp() {

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
