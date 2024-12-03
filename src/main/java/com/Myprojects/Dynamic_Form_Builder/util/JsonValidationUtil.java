package com.Myprojects.Dynamic_Form_Builder.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.IOException;
import java.util.Map;

public class JsonValidationUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void validateJson(String jsonSchemaStr, Map<String, Object> jsonData) throws IOException, ProcessingException {
        JsonNode schemaNode = JsonLoader.fromString(jsonSchemaStr);

        String jsonDataStr = mapper.writeValueAsString(jsonData);
        JsonNode dataNode = JsonLoader.fromString(jsonDataStr);

        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        JsonSchema schema = factory.getJsonSchema(schemaNode);

        var report = schema.validate(dataNode);
        if (!report.isSuccess()) {
            throw new IllegalArgumentException("JSON validation failed: " + report);
        }
    }

}
