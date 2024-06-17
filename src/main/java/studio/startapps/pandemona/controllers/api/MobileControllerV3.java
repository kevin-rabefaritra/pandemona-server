package studio.startapps.pandemona.controllers.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v3")
public class MobileControllerV3 {

    @GetMapping("/hello")
    public ResponseEntity<String> fetch() {
        return new ResponseEntity<String>("hello.", HttpStatus.OK);
    }
}
