package studio.startapps.pandemona.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import studio.startapps.pandemona.models.Drugstore;
import studio.startapps.pandemona.repositories.DrugstoreRepository;

import java.util.List;

@Controller
@RequestMapping(path = "/drugstores")
public class DrugstoreController {

    @Autowired
    private DrugstoreRepository drugstoreRepository;

    @GetMapping(path = "/")
    public String index(Model model) {
        List<Drugstore> drugstoreList = this.drugstoreRepository.findAll();

        model.addAttribute("drugstores", drugstoreList);
        return "drugstores/index";
    }
}
