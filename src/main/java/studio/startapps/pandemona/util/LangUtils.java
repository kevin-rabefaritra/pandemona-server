package studio.startapps.pandemona.util;

import java.util.List;
import java.util.stream.Stream;

public interface LangUtils {

    static String getFirstSupportedLangs(String requestedLangs, String[] supportedLangs) {
        String[] requestedLangArray = requestedLangs.split(",");
        return Stream.of(requestedLangArray).filter((lang) -> List.of(supportedLangs).contains(lang)).findFirst()
                .orElse(supportedLangs[0]);
    }
}
