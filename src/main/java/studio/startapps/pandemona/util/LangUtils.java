package studio.startapps.pandemona.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.stream.Stream;

public interface LangUtils {

    static String getFirstSupportedLangs(String requestedLangs, String[] supportedLangs) {
        String[] requestedLangArray = requestedLangs.split(",");
        return Stream.of(requestedLangArray).filter((lang) -> List.of(supportedLangs).contains(lang)).findFirst()
                .orElse(supportedLangs[0]);
    }

    static String getContentForLang(JsonNode jsonNode, String lang) {
        if (jsonNode == null) {
            return null;
        }

        JsonNode node = jsonNode.get(lang);
        if (node == null) {
            // return the first lang
            return getContentForLang(jsonNode, jsonNode.fieldNames().next());
        }
        return node.asText();
    }
}
