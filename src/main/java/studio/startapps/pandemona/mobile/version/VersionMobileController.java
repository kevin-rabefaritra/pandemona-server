package studio.startapps.pandemona.mobile.version;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.mobile.version.internal.AppVersion;

@RestController
@RequestMapping(value = "api/mobile/version", version = "2.0")
@RequiredArgsConstructor
public class VersionMobileController {

    private final VersionMobileService versionMobileService;

    @GetMapping
    AppVersion getVersion() {
        return this.versionMobileService.getVersion();
    }
}
