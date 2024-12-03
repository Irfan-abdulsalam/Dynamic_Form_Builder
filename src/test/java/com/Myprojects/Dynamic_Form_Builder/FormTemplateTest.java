package com.Myprojects.Dynamic_Form_Builder;

import com.Myprojects.Dynamic_Form_Builder.entity.FormTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FormTemplateTest {

    private FormTemplate formTemplate;

    @BeforeEach
    public void setUp() {
        formTemplate = new FormTemplate();
    }

    @Test
    public void testPrePersist() {
        formTemplate.setId(null);
        formTemplate.setFormName("Test Form");
        formTemplate.setCreatedBy("user1");
        formTemplate.onCreate();

        assertNotNull(formTemplate.getId(), "ID should be generated.");
        assertNotNull(formTemplate.getCreatedAt(), "CreatedAt should be set.");
        assertEquals("user1", formTemplate.getCreatedBy(), "CreatedBy should match.");
    }

    @Test
    public void testPreUpdate() {
        formTemplate.setUpdatedAt(LocalDateTime.now().minusDays(1));
        formTemplate.onUpdate();

        assertNotNull(formTemplate.getUpdatedAt(), "UpdatedAt should be updated.");
        assertTrue(formTemplate.getUpdatedAt().isAfter(LocalDateTime.now().minusDays(1)),
                "UpdatedAt should be after the previous value.");
    }

    @Test
    public void testSetAndGetFormSchemaAsMap() throws IOException {
        Map<String, Object> schemaMap = Map.of("field1", "value1", "field2", "value2");
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonSchema = objectMapper.writeValueAsString(schemaMap);
        formTemplate.setFormSchema(jsonSchema);

        Map<String, Object> resultMap = formTemplate.getFormSchemaAsMap();
        assertEquals(schemaMap, resultMap, "Form schema map should match the expected values.");
    }


    @Test
    public void testIdGenerationWhenNull() {
        formTemplate.setId(null);
        formTemplate.onCreate();

        assertNotNull(formTemplate.getId(), "ID should be generated if null.");
        assertTrue(formTemplate.getId().length() > 0, "Generated ID should not be empty.");
    }

    @Test
    public void testIdNotOverwrittenWhenSet() {
        formTemplate.setId("existing-id");
        formTemplate.onCreate();

        assertEquals("existing-id", formTemplate.getId(), "ID should not be overwritten if already set.");
    }
}
