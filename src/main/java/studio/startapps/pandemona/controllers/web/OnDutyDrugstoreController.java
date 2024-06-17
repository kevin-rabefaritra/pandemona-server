package studio.startapps.pandemona.controllers.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import studio.startapps.pandemona.models.Drugstore;
import studio.startapps.pandemona.models.OnDutyDrugstores;
import studio.startapps.pandemona.repositories.DrugstoreRepository;
import studio.startapps.pandemona.repositories.OnDutyDrugstoresRepository;

import java.time.LocalDate;

@Controller
@RequestMapping("/onduty-drugstores")
public class OnDutyDrugstoreController {

    private final Logger logger;

    private final static int PAGE_SIZE = 10;
    private final static String SORT_BY = "endDate";

    private final OnDutyDrugstoresRepository onDutyDrugstoresRepository;
    private final DrugstoreRepository drugstoreRepository;

    public OnDutyDrugstoreController(
            OnDutyDrugstoresRepository onDutyDrugstoresRepository,
            DrugstoreRepository drugstoreRepository
    ) {
        this.logger = LoggerFactory.getLogger(this.getClass());

        this.onDutyDrugstoresRepository = onDutyDrugstoresRepository;
        this.drugstoreRepository = drugstoreRepository;
    }

    @GetMapping("")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {
        Sort sort = Sort.by(SORT_BY).descending();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);

        Page<OnDutyDrugstores> onDutyDrugstores = onDutyDrugstoresRepository.findAll(pageable);
        model.addAttribute("onDutyDrugstoresList", onDutyDrugstores);
        return "onduty-drugstores/index";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        OnDutyDrugstores onDutyDrugstores = new OnDutyDrugstores();
        onDutyDrugstores.setStartDate(LocalDate.now());
        onDutyDrugstores.setEndDate(LocalDate.now().plusDays(7));

        Iterable<Drugstore> drugstoreList = drugstoreRepository.findAll();

        model.addAttribute("onDutyDrugstores", onDutyDrugstores);
        model.addAttribute("drugstores", drugstoreList);
        model.addAttribute("title", "Add on-duty drugstores");
        return "onduty-drugstores/form";
    }

    @PostMapping("/create")
    public String createSubmit(@ModelAttribute OnDutyDrugstores onDutyDrugstores) {
        onDutyDrugstoresRepository.save(onDutyDrugstores);
        return "redirect:/onduty-drugstores";
    }

    @GetMapping("/{onDutyDrugstoresId}/edit")
    public String updateForm(@PathVariable long onDutyDrugstoresId, Model model) {
        OnDutyDrugstores onDutyDrugstores = onDutyDrugstoresRepository.findById(onDutyDrugstoresId).get();
        Iterable<Drugstore> drugstores = drugstoreRepository.findAll();

        model.addAttribute("onDutyDrugstores", onDutyDrugstores);
        model.addAttribute("drugstores", drugstores);
        return "onduty-drugstores/form";
    }

    @PostMapping("/{onDutyDrugstoresId}/edit")
    public String updateSubmit(@PathVariable long onDutyDrugstoresId, @ModelAttribute OnDutyDrugstores onDutyDrugstores) {
        onDutyDrugstores.setId(onDutyDrugstoresId);
        onDutyDrugstoresRepository.save(onDutyDrugstores);
        return "redirect:/onduty-drugstores";
    }

    @RequestMapping(value = "/{onDutyDrugstoresId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable long onDutyDrugstoresId) {
        onDutyDrugstoresRepository.deleteById(onDutyDrugstoresId);
        return "redirect:/onduty-drugstores";
    }
}
