package studio.startapps.pandemona.drugstore;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/drugstores")
public class DrugstoreController {

    private final static int PAGE_SIZE = 20;
    private final static String SORT_BY = "id";

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
        return "redirect:/drugstores";
    }

    @GetMapping(path = "/{drugstoreId}/edit")
    public String updateForm(@PathVariable long drugstoreId, Model model) {
        Drugstore drugstore = this.drugstoreService.findFirstById(drugstoreId);

        model.addAttribute("title", "Edit drugstore");
        model.addAttribute("drugstore", drugstore);
        return "drugstores/form";
    }

    @PostMapping(path = "/{drugstoreId}/edit")
    public String updateSubmit(@PathVariable long drugstoreId, @ModelAttribute Drugstore drugstore) {
        drugstore.setId(drugstoreId);
        drugstoreService.save(drugstore);
        return "redirect:/drugstores";
    }

    @RequestMapping(path = "/{drugstoreId}", method = RequestMethod.DELETE)
    public String deleteSubmit(@PathVariable long drugstoreId) {
        drugstoreService.deleteById(drugstoreId);
        return "redirect:/drugstores";
    }
}
