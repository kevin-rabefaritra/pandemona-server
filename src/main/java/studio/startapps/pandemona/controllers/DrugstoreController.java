package studio.startapps.pandemona.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import studio.startapps.pandemona.models.City;
import studio.startapps.pandemona.models.Drugstore;
import studio.startapps.pandemona.repositories.DrugstoreRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping(path = "/drugstores")
public class DrugstoreController {

    @Autowired
    private DrugstoreRepository drugstoreRepository;

    @GetMapping(path = "/")
    public String index(Model model) {
        Iterable<Drugstore> drugstoreList = this.drugstoreRepository.findAll();

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
    public String createSubmit(@ModelAttribute Drugstore drugstore, Model model) {
        this.drugstoreRepository.save(drugstore);
        return "redirect:/drugstores/";
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
        return "redirect:/drugstores/";
    }
}
