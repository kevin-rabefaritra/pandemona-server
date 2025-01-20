package studio.startapps.pandemona.healthcenter.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import studio.startapps.pandemona.business.exception.BusinessNotFoundException;
import studio.startapps.pandemona.healthcenter.internal.HealthCenter;
import studio.startapps.pandemona.healthcenter.internal.HealthCenterRepository;
import studio.startapps.pandemona.healthcenter.internal.HealthCenterRequest;

import java.util.List;

@RestController
@RequestMapping("/api/health-centers")
@RequiredArgsConstructor
public class HealthCenterController {

    private final HealthCenterService healthCenterService;

    @GetMapping
    Page<HealthCenterPreview> findAll(Pageable pageable) {
        return this.healthCenterService.findAll(pageable);
    }

    @GetMapping("/types")
    List<String> getTypes() {
        return this.healthCenterService.getTypes();
    }

    @GetMapping("/{id}")
    HealthCenterDetails findById(@PathVariable long id) throws BusinessNotFoundException {
        return this.healthCenterService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void save(@RequestBody HealthCenterRequest request) {
        this.healthCenterService.save(request);
    }

    @PutMapping("/{id}")
    void update(@PathVariable long id, @RequestBody HealthCenterRequest request) throws BusinessNotFoundException {
        this.healthCenterService.update(id, request);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) throws BusinessNotFoundException {
        this.healthCenterService.delete(id);
    }
}
