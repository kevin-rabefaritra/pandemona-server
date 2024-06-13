package studio.startapps.pandemona.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import studio.startapps.pandemona.models.Drugstore;
import studio.startapps.pandemona.repositories.DrugstoreRepository;

@Controller
@RequestMapping(path = "/drugstores")
public class DrugstoreController {

    private final static int PAGE_SIZE = 10;
    private final static String SORT_BY = "id";

    private final DrugstoreRepository drugstoreRepository;

    public DrugstoreController(DrugstoreRepository drugstoreRepository) {
        this.drugstoreRepository = drugstoreRepository;
    }

    @GetMapping(path = "")
    public String index(@RequestParam(defaultValue = "0") int page, Model model) {
        Sort sort = Sort.by(SORT_BY);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);

        Page<Drugstore> drugstoreList = this.drugstoreRepository.findAll(pageable);

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
        this.drugstoreRepository.save(drugstore);
        return "redirect:/drugstores";
    }

    @GetMapping(path = "/{drugstoreId}/edit")
    public String updateForm(@PathVariable long drugstoreId, Model model) {
        Drugstore drugstore = this.drugstoreRepository.findFirstById(drugstoreId);
        model.addAttribute("title", "Edit drugstore");
        model.addAttribute("drugstore", drugstore);
        return "drugstores/form";
    }

    @PostMapping(path = "/{drugstoreId}/edit")
    public String updateSubmit(@PathVariable long drugstoreId, @ModelAttribute Drugstore drugstore) {
        drugstore.setId(drugstoreId);
        drugstoreRepository.save(drugstore);
        return "redirect:/drugstores";
    }

    @RequestMapping(path = "/{drugstoreId}", method = RequestMethod.DELETE)
    public String deleteSubmit(@PathVariable long drugstoreId) {
        drugstoreRepository.deleteById(drugstoreId);
        return "redirect:/drugstores";
    }
}
