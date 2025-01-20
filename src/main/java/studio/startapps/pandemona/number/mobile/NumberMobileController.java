package studio.startapps.pandemona.number.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mobile/v1/numbers")
@RequiredArgsConstructor
public class NumberMobileController {

    private final NumberMobileService numberMobileService;

    @GetMapping
    List<EmergencyNumberItem> findAll() {
        return this.numberMobileService.findAll();
    }
}
