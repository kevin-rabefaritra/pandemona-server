package studio.startapps.pandemona.mobile.version;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.mobile.version.internal.AppVersion;

@RestController
@RequestMapping("/api/mobile/v1/version")
@RequiredArgsConstructor
@Deprecated
public class VersionMobileControllerV1 {

    private final VersionMobileService versionMobileService;

    @GetMapping
    AppVersion getVersion() {
        return this.versionMobileService.getVersion();
    }
}
