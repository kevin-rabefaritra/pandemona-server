package studio.startapps.pandemona.version.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.version.internal.AppVersion;

@RestController
@RequestMapping("/api/mobile/v1/version")
@RequiredArgsConstructor
public class VersionMobileController {

    private final VersionMobileService versionMobileService;

    @GetMapping
    AppVersion getVersion() {
        return this.versionMobileService.getVersion();
    }
}
