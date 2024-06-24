package studio.startapps.pandemona.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login() {
        return "authentication/auth-signin-cover";
    }

    // @PostMapping("/login") already handled by Spring

    @GetMapping("/home")
    public String home() {
        return "redirect:/drugstores";
    }
}
