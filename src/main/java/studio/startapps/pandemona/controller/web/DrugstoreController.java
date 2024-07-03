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

import java.util.Optional;

@Controller
@RequestMapping(path = "/drugstores")
public class DrugstoreController {

    private static final int PAGE_SIZE = 20;
    private static final String SORT_BY = "id";

    private static final String URL_REDIRECT_MAIN = "redirect:/drugstores";

    private final DrugstoreService drugstoreService;

    public DrugstoreController(DrugstoreService drugstoreService) {
        this.drugstoreService = drugstoreService;
    }

    @GetMapping(path = "")
    public String index(@RequestParam(defaultValue = "0") int page, Model model) {
        Sort sort = Sort.by(SORT_BY);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);

        Page<Drugstore> drugstoreList = this.drugstoreService.findAll(pageable);

        model.addAttribute("drugstores", drugstoreList);
        return "drugstores/index";
    }

    /**
     * Add a new drugstore
     * @return String
     */
    @GetMapping(path = "/create")
    public String createForm(Model model) {
        model.addAttribute("title", "New drugstore");
        model.addAttribute("drugstore", new Drugstore());
        return "drugstores/form";
    }

    /**
     * Saves a new drugstore
     */
    @PostMapping(path = "/create")
    public String createSubmit(@ModelAttribute Drugstore drugstore) {
        this.drugstoreService.save(drugstore);
        return URL_REDIRECT_MAIN;
    }

    @GetMapping(path = "/{drugstoreId}/edit")
    public String updateForm(@PathVariable long drugstoreId, Model model) {
        Optional<Drugstore> drugstore = this.drugstoreService.findFirstById(drugstoreId);

        if (drugstore.isPresent()) {
            model.addAttribute("title", "Edit drugstore");
            model.addAttribute("drugstore", drugstore.get());
            return "drugstores/form";
        }
        else {
            return URL_REDIRECT_MAIN;
        }
    }

    @PostMapping(path = "/{drugstoreId}/edit")
    public String updateSubmit(@PathVariable long drugstoreId, @ModelAttribute Drugstore drugstore) {
        drugstore.setId(drugstoreId);
        drugstoreService.save(drugstore);
        return URL_REDIRECT_MAIN;
    }

    @RequestMapping(path = "/{drugstoreId}", method = RequestMethod.DELETE)
    public String deleteSubmit(@PathVariable long drugstoreId) {
        drugstoreService.deleteById(drugstoreId);
        return URL_REDIRECT_MAIN;
    }
}
