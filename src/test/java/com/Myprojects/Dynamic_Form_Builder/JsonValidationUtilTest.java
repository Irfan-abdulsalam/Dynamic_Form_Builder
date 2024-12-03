package com.Myprojects.Dynamic_Form_Builder;


import com.Myprojects.Dynamic_Form_Builder.util.JsonValidationUtil;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonValidationUtilTest {

    private static final String VALID_SCHEMA = """
        {
          "$schema": "http://json-schema.org/draft-07/schema#",
          "type": "object",
          "properties": {
            "name": { "type": "string" },
            "age": { "type": "integer" },
            "email": { "type": "string", "format": "email" }
          },
          "required": ["name", "age"]
        }
    """;

    @Test
    void testValidateJsonWithValidData() {
        Map<String, Object> validData = new HashMap<>();
        validData.put("name", "John Doe");
        validData.put("age", 25);
        validData.put("email", "johndoe@example.com");

        assertDoesNotThrow(() -> JsonValidationUtil.validateJson(VALID_SCHEMA, validData));
    }

    @Test
    void testValidateJsonWithMissingRequiredField() {
        Map<String, Object> invalidData = new HashMap<>();
        invalidData.put("age", 25);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> JsonValidationUtil.validateJson(VALID_SCHEMA, invalidData));
        assertTrue(exception.getMessage().contains("JSON validation failed"));
    }

    @Test
    void testValidateJsonWithInvalidFieldType() {
        Map<String, Object> invalidData = new HashMap<>();
        invalidData.put("name", "John Doe");
        invalidData.put("age", "twenty-five");

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> JsonValidationUtil.validateJson(VALID_SCHEMA, invalidData));
        assertTrue(exception.getMessage().contains("JSON validation failed"));
    }

    @Test
    void testValidateJsonWithInvalidEmailFormat() {
        Map<String, Object> invalidData = new HashMap<>();
        invalidData.put("name", "John Doe");
        invalidData.put("age", 25);
        invalidData.put("email", "not-an-email");

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> JsonValidationUtil.validateJson(VALID_SCHEMA, invalidData));
        assertTrue(exception.getMessage().contains("JSON validation failed"));
    }


}
