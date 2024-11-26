package studio.startapps.pandemona.drugstore;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import studio.startapps.pandemona.drugstore.internal.DrugstoreNotFoundException;
import studio.startapps.pandemona.drugstore.dto.OnDutyDrugstoresItemDTO;
import studio.startapps.pandemona.drugstore.internal.CityEnum;

import java.util.*;

@RestController
@RequestMapping(path = "/api/drugstores")
public class DrugstoreController {

    private final DrugstoreService drugstoreService;

    private static final int ITEMS_PER_PAGE = 20;
    private static final String DEFAULT_SORT = "id";

    public DrugstoreController(DrugstoreService drugstoreService) {
        this.drugstoreService = drugstoreService;
    }

    @GetMapping
    public Page<Drugstore> index(@PageableDefault(size = ITEMS_PER_PAGE, sort = DEFAULT_SORT) Pageable page) {
        return this.drugstoreService.findAll(page);
    }

    /**
     * Saves a new drugstore
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody Drugstore drugstore) {
        this.drugstoreService.save(drugstore);
    }

    @PutMapping(path = "/{drugstoreId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long drugstoreId, @Valid @RequestBody Drugstore drugstore) {
        drugstore.setId(drugstoreId);
        drugstoreService.save(drugstore);
    }

    @GetMapping(path = "/{drugstoreId}")
    public Drugstore get(@PathVariable long drugstoreId) {
        return this.drugstoreService.findFirstById(drugstoreId)
                .orElseThrow(() -> new DrugstoreNotFoundException(drugstoreId));
    }

    @RequestMapping(path = "/{drugstoreId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long drugstoreId) {
        drugstoreService.deleteById(drugstoreId);
    }

    @GetMapping(path = "/cities")
    public List<CityEnum> getCities() {
        return List.of(CityEnum.values());
    }

    @GetMapping(path = "/all")
    public List<OnDutyDrugstoresItemDTO> getAll() {
        return this.drugstoreService.findAllAsDTO();
    }
}
