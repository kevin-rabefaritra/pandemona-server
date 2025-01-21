package studio.startapps.pandemona.number.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import studio.startapps.pandemona.business.exception.BusinessNotFoundException;
import studio.startapps.pandemona.number.internal.EmergencyNumberRequest;

import java.util.List;

@RestController
@RequestMapping("/api/numbers")
@RequiredArgsConstructor
public class NumberController {

    private static final int ITEMS_PER_PAGE = 20;
    private static final String DEFAULT_SORT = "name";

    private final NumberService numberService;

    @GetMapping
    Page<EmergencyNumberPreview> findAll(@PageableDefault(size = ITEMS_PER_PAGE, sort = DEFAULT_SORT) Pageable pageable) {
        return this.numberService.findAll(pageable);
    }

    @GetMapping("/types")
    List<String> getTypes() {
        return this.numberService.getTypes();
    }

    @GetMapping("/{id}")
    EmergencyNumberDetails findById(@PathVariable long id) throws BusinessNotFoundException {
        return this.numberService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void save(@RequestBody EmergencyNumberRequest request) {
        this.numberService.save(request);
    }

    @PutMapping("/{id}")
    void update(@PathVariable long id, @RequestBody EmergencyNumberRequest request) throws BusinessNotFoundException {
        this.numberService.update(id, request);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) throws BusinessNotFoundException {
        this.numberService.delete(id);
    }
}
