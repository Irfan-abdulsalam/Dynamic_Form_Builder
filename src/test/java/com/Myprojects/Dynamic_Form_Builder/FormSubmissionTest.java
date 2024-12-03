package com.Myprojects.Dynamic_Form_Builder;

import com.Myprojects.Dynamic_Form_Builder.entity.FormSubmission;
import com.Myprojects.Dynamic_Form_Builder.entity.FormTemplate;
import com.Myprojects.Dynamic_Form_Builder.util.Base62;
import com.Myprojects.Dynamic_Form_Builder.util.JsonConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FormSubmissionTest {

    private FormSubmission formSubmission;

    @BeforeEach
    public void setUp() {
        formSubmission = new FormSubmission();
    }

    @Test
    public void testPrePersist() {
        formSubmission.setId(null);
        formSubmission.setSubmittedBy("user1");
        formSubmission.setSubmissionData(Map.of("field1", "value1"));
        formSubmission.onCreate(); // Simulate pre-persist hook

        assertNotNull(formSubmission.getId(), "ID should be generated.");
        assertNotNull(formSubmission.getSubmittedAt(), "SubmittedAt should be set.");
        assertEquals("user1", formSubmission.getSubmittedBy(), "SubmittedBy should be user1.");
    }

    @Test
    public void testPreUpdate() {
        formSubmission.setId("some-id");
        formSubmission.setUpdatedBy("user2");
        formSubmission.setUpdatedAt(LocalDateTime.now().minusDays(1));
        formSubmission.onUpdate();

        assertNotNull(formSubmission.getUpdatedAt(), "UpdatedAt should be updated.");
        assertEquals("user2", formSubmission.getUpdatedBy(), "UpdatedBy should be user2.");
    }

    @Test
    public void testSetAndGetSubmissionData() {
        Map<String, Object> data = Map.of("field1", "value1");
        formSubmission.setSubmissionData(data);
        assertEquals(data, formSubmission.getSubmissionData(), "Submission data should match.");
    }

    @Test
    public void testSetAndGetFormTemplate() {
        FormTemplate formTemplate = Mockito.mock(FormTemplate.class);
        formSubmission.setFormTemplate(formTemplate);
        assertEquals(formTemplate, formSubmission.getFormTemplate(), "FormTemplate should match.");
    }

    @Test
    public void testIdGenerationOnNullId() {
        formSubmission.setId(null);
        formSubmission.onCreate();
        String generatedId = formSubmission.getId();
        assertNotNull(generatedId, "ID should be generated if null.");
        assertTrue(generatedId.length() > 0, "Generated ID should not be empty.");
    }

    @Test
    public void testIdNotNullWhenAlreadySet() {
        formSubmission.setId("existing-id");
        formSubmission.onCreate();
        assertEquals("existing-id", formSubmission.getId(), "ID should not be overwritten.");
    }

}
