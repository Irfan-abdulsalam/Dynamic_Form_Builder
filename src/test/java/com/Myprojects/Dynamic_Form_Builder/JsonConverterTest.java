package com.Myprojects.Dynamic_Form_Builder;


import com.Myprojects.Dynamic_Form_Builder.util.JsonConverter;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonConverterTest {

    private final JsonConverter jsonConverter = new JsonConverter();

    @Test
    void testConvertToDatabaseColumn() {
        Map<String, Object> attribute = new HashMap<>();
        attribute.put("key1", "value1");
        attribute.put("key2", 123);
        attribute.put("key3", true);

        String json = jsonConverter.convertToDatabaseColumn(attribute);

        assertNotNull(json);
        assertTrue(json.contains("\"key1\":\"value1\""));
        assertTrue(json.contains("\"key2\":123"));
        assertTrue(json.contains("\"key3\":true"));
    }

    @Test
    void testConvertToEntityAttribute() {
        String json = "{\"key1\":\"value1\",\"key2\":123,\"key3\":true}";

        Map<String, Object> attribute = jsonConverter.convertToEntityAttribute(json);

        assertNotNull(attribute);
        assertEquals("value1", attribute.get("key1"));
        assertEquals(123, attribute.get("key2"));
        assertEquals(true, attribute.get("key3"));
    }

    @Test
    void testConvertToEntityAttributeWithInvalidJson() {
        String invalidJson = "invalid-json";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> jsonConverter.convertToEntityAttribute(invalidJson));
        assertTrue(exception.getMessage().contains("Failed to convert JSON String to Map"));
    }
}
