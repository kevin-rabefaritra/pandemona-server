package studio.startapps.pandemona.version.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.properties.MobileAppProperties;
import studio.startapps.pandemona.version.internal.AppVersion;

@Service
@RequiredArgsConstructor
public class VersionMobileService {

    private final MobileAppProperties appProperties;

    AppVersion getVersion() {
        return new AppVersion(
            this.appProperties.getLastVersionNumber(),
            this.appProperties.getLastVersionCode()
        );
    }
}
