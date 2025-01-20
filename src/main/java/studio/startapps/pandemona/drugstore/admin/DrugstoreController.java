package studio.startapps.pandemona.drugstore.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import studio.startapps.pandemona.business.exception.BusinessNotFoundException;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstoresItemPreview;

import java.util.List;

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
    public Page<DrugstorePreview> index(@PageableDefault(size = ITEMS_PER_PAGE, sort = DEFAULT_SORT) Pageable page) {
        return this.drugstoreService.findAll(page);
    }

    @GetMapping("/features")
    public List<String> getFeatures() {
        return this.drugstoreService.getFeatures();
    }

    @GetMapping("/search")
    public List<DrugstorePreview> search(@RequestParam(name = "q") String keyword) {
        return this.drugstoreService.findByKeyword(keyword);
    }

    /**
     * Saves a new drugstore
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody DrugstoreRequest request) {
        this.drugstoreService.save(request);
    }

    @PutMapping(path = "/{drugstoreId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long drugstoreId, @RequestBody DrugstoreRequest request) throws BusinessNotFoundException {
        drugstoreService.update(drugstoreId, request);
    }

    @GetMapping(path = "/{drugstoreId}")
    public DrugstoreDetails get(@PathVariable long drugstoreId) throws BusinessNotFoundException {
        return this.drugstoreService.findById(drugstoreId);
    }

    @DeleteMapping("/{drugstoreId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long drugstoreId) {
        drugstoreService.deleteById(drugstoreId);
    }
}
