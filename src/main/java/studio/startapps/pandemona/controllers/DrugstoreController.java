package studio.startapps.pandemona.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import studio.startapps.pandemona.models.Drugstore;
import studio.startapps.pandemona.repositories.DrugstoreRepository;

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
     * @param Model
     * @return String
     */
    @GetMapping(path = "/add")
    public String add(Model model) {
        Drugstore drugstore = new Drugstore();
        model.addAttribute("drugstore", drugstore);

        return "drugstores/form";
    }
}
