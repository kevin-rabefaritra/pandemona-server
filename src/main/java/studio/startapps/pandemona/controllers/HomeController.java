package studio.startapps.pandemona.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login(Model model) {
        return "authentication/auth-signin-cover";
    }
}
