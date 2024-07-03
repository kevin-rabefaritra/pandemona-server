package studio.startapps.pandemona.controller.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import studio.startapps.pandemona.repository.entity.Drugstore;
import studio.startapps.pandemona.service.DrugstoreService;
import studio.startapps.pandemona.repository.entity.OnDutyDrugstores;
import studio.startapps.pandemona.service.OnDutyDrugstoresService;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/onduty-drugstores")
public class OnDutyDrugstoreController {

    private static final int PAGE_SIZE = 10;
    private static final String SORT_BY = "endDate";
    private static final String URL_REDIRECT_MAIN = "redirect:/onduty-drugstores";

    private final OnDutyDrugstoresService onDutyDrugstoresService;
    private final DrugstoreService drugstoreService;

    public OnDutyDrugstoreController(
            OnDutyDrugstoresService onDutyDrugstoresService,
            DrugstoreService drugstoreService
    ) {
        this.onDutyDrugstoresService = onDutyDrugstoresService;
        this.drugstoreService = drugstoreService;
    }

    @GetMapping("")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {
        Sort sort = Sort.by(SORT_BY).descending();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);

        Page<OnDutyDrugstores> onDutyDrugstores = onDutyDrugstoresService.findAll(pageable);
        model.addAttribute("onDutyDrugstoresList", onDutyDrugstores);
        return "onduty-drugstores/index";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        LocalDate defaultStartDate = this.onDutyDrugstoresService.getNextStartDate();
        LocalDate defaultEndDate = defaultStartDate.plusWeeks(1);

        OnDutyDrugstores onDutyDrugstores = new OnDutyDrugstores();
        onDutyDrugstores.setStartDate(defaultStartDate);
        onDutyDrugstores.setEndDate(defaultEndDate);

        Iterable<Drugstore> drugstoreList = drugstoreService.findAll();

        model.addAttribute("onDutyDrugstores", onDutyDrugstores);
        model.addAttribute("drugstores", drugstoreList);
        model.addAttribute("defaultStartDate", defaultStartDate);
        model.addAttribute("defaultEndDate", defaultEndDate);

        model.addAttribute("title", "Add on-duty drugstores");
        return "onduty-drugstores/form";
    }

    @PostMapping("/create")
    public String createSubmit(@ModelAttribute OnDutyDrugstores onDutyDrugstores) {
        onDutyDrugstoresService.save(onDutyDrugstores);
        return URL_REDIRECT_MAIN;
    }

    @GetMapping("/{onDutyDrugstoresId}/edit")
    public String updateForm(@PathVariable long onDutyDrugstoresId, Model model) {
        Optional<OnDutyDrugstores> onDutyDrugstores = onDutyDrugstoresService.findById(onDutyDrugstoresId);

        if (onDutyDrugstores.isPresent()) {
            Iterable<Drugstore> drugstores = drugstoreService.findAll();
            model.addAttribute("onDutyDrugstores", onDutyDrugstores);
            model.addAttribute("drugstores", drugstores);
            return "onduty-drugstores/form";
        }
        else {
            return URL_REDIRECT_MAIN;
        }
    }

    @PostMapping("/{onDutyDrugstoresId}/edit")
    public String updateSubmit(@PathVariable long onDutyDrugstoresId, @ModelAttribute OnDutyDrugstores onDutyDrugstores) {
        onDutyDrugstores.setId(onDutyDrugstoresId);
        onDutyDrugstoresService.save(onDutyDrugstores);
        return URL_REDIRECT_MAIN;
    }

    @RequestMapping(value = "/{onDutyDrugstoresId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable long onDutyDrugstoresId) {
        onDutyDrugstoresService.deleteById(onDutyDrugstoresId);
        return URL_REDIRECT_MAIN;
    }
}
