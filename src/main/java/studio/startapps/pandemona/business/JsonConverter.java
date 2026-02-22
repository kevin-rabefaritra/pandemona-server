package studio.startapps.pandemona.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class JsonConverter implements AttributeConverter<JsonNode, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(JsonNode jsonNode) {
        try {
            return objectMapper.writeValueAsString(jsonNode);
        }
        catch (JsonProcessingException e) {
            log.error("[HashMapConverter] Error while converting {}", jsonNode.toString());
            return null;
        }
    }

    @Override
    public JsonNode convertToEntityAttribute(String s) {
        try {
            return objectMapper.readTree(s);
        }
        catch (JsonProcessingException e) {
            log.error("[HashMapConverter] Error while reading {}", s);
            return null;
        }
    }
}