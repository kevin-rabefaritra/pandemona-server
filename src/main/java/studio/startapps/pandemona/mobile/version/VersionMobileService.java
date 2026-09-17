package studio.startapps.pandemona.mobile.version;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.core.configuration.MobileAppProperties;
import studio.startapps.pandemona.mobile.version.internal.AppVersion;

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
