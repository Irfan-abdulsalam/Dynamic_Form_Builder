package com.Myprojects.Dynamic_Form_Builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.Myprojects.Dynamic_Form_Builder.entity.FormSubmission;
import com.Myprojects.Dynamic_Form_Builder.entity.FormTemplate;
import com.Myprojects.Dynamic_Form_Builder.repository.FormSubmissionRepository;
import com.Myprojects.Dynamic_Form_Builder.repository.FormTemplateRepository;
import com.Myprojects.Dynamic_Form_Builder.service.FormService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

class FormServiceTest {

    @Mock
    private FormTemplateRepository formTemplateRepository;

    @Mock
    private FormSubmissionRepository formSubmissionRepository;

    @InjectMocks
    private FormService formService;

    private Map<String, Object> formSchema;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        formSchema = new HashMap<>();
        formSchema.put("field1", "String");
        formSchema.put("field2", "Integer");
    }

    @Test
    void testCreateForm() throws Exception {
        String formName = "Sample Form";
        FormTemplate mockFormTemplate = new FormTemplate();
        mockFormTemplate.setFormName(formName);
        mockFormTemplate.setFormSchema(new ObjectMapper().writeValueAsString(formSchema));

        when(formTemplateRepository.save(any(FormTemplate.class))).thenReturn(mockFormTemplate);

        FormTemplate createdForm = formService.createForm(formName, formSchema);

        assertNotNull(createdForm);
        assertEquals(formName, createdForm.getFormName());
        assertEquals(new ObjectMapper().writeValueAsString(formSchema), createdForm.getFormSchema());
        verify(formTemplateRepository, times(1)).save(any(FormTemplate.class));
    }

    @Test
    void testSubmitForm() throws Exception {
        String formTemplateId = "form1";
        Map<String, Object> submissionData = new HashMap<>();
        submissionData.put("field1", "John Doe");
        submissionData.put("field2", 25);

        FormTemplate mockFormTemplate = new FormTemplate();
        mockFormTemplate.setFormName("Sample Form");
        mockFormTemplate.setFormSchema(new ObjectMapper().writeValueAsString(formSchema));

        FormSubmission mockFormSubmission = new FormSubmission();
        mockFormSubmission.setFormTemplate(mockFormTemplate);
        mockFormSubmission.setSubmissionData(submissionData);

        when(formTemplateRepository.findById(formTemplateId)).thenReturn(java.util.Optional.of(mockFormTemplate));
        when(formSubmissionRepository.save(any(FormSubmission.class))).thenReturn(mockFormSubmission);

        FormSubmission submittedForm = formService.submitForm(formTemplateId, submissionData);

        assertNotNull(submittedForm);
        assertEquals(submissionData, submittedForm.getSubmissionData());
        assertEquals(mockFormTemplate, submittedForm.getFormTemplate());
        verify(formSubmissionRepository, times(1)).save(any(FormSubmission.class));
    }

    @Test
    void testUpdateSubmission() throws Exception {
        String submissionId = "submission1";
        Map<String, Object> updatedSubmissionData = new HashMap<>();
        updatedSubmissionData.put("field1", "Jane Doe");
        updatedSubmissionData.put("field2", 30);

        FormSubmission existingSubmission = new FormSubmission();
        existingSubmission.setSubmissionData(updatedSubmissionData);

        FormTemplate mockFormTemplate = new FormTemplate();
        mockFormTemplate.setFormName("Sample Form");
        mockFormTemplate.setFormSchema(new ObjectMapper().writeValueAsString(formSchema));
        existingSubmission.setFormTemplate(mockFormTemplate);

        when(formSubmissionRepository.findById(submissionId)).thenReturn(java.util.Optional.of(existingSubmission));
        when(formSubmissionRepository.save(any(FormSubmission.class))).thenReturn(existingSubmission);

        FormSubmission updatedSubmission = formService.updateSubmission(submissionId, updatedSubmissionData);

        assertNotNull(updatedSubmission);
        assertEquals(updatedSubmissionData, updatedSubmission.getSubmissionData());
        verify(formSubmissionRepository, times(1)).save(any(FormSubmission.class));
    }

    @Test
    void testDeleteSubmission() {
        String submissionId = "submission1";

        FormSubmission mockFormSubmission = new FormSubmission();
        mockFormSubmission.setId(submissionId);

        when(formSubmissionRepository.findById(submissionId)).thenReturn(java.util.Optional.of(mockFormSubmission));

        formService.deleteSubmission(submissionId);

        verify(formSubmissionRepository, times(1)).delete(mockFormSubmission);
    }

    @Test
    void testUpdateForm() throws Exception {
        String formId = "form1";
        String newFormName = "Updated Form";
        Map<String, Object> newFormSchema = new HashMap<>();
        newFormSchema.put("field1", "String");
        newFormSchema.put("field2", "Boolean");

        FormTemplate existingFormTemplate = new FormTemplate();
        existingFormTemplate.setFormName("Sample Form");
        existingFormTemplate.setFormSchema(new ObjectMapper().writeValueAsString(formSchema));

        FormTemplate updatedFormTemplate = new FormTemplate();
        updatedFormTemplate.setFormName(newFormName);
        updatedFormTemplate.setFormSchema(new ObjectMapper().writeValueAsString(newFormSchema));

        when(formTemplateRepository.findById(formId)).thenReturn(java.util.Optional.of(existingFormTemplate));
        when(formTemplateRepository.save(any(FormTemplate.class))).thenReturn(updatedFormTemplate);

        FormTemplate result = formService.updateForm(formId, newFormName, newFormSchema);

        assertNotNull(result);
        assertEquals(newFormName, result.getFormName());
        assertEquals(new ObjectMapper().writeValueAsString(newFormSchema), result.getFormSchema());
        verify(formTemplateRepository, times(1)).save(any(FormTemplate.class));
    }

    @Test
    void testGetAllForms() {
        FormTemplate formTemplate1 = new FormTemplate();
        formTemplate1.setFormName("Form 1");
        FormTemplate formTemplate2 = new FormTemplate();
        formTemplate2.setFormName("Form 2");

        List<FormTemplate> mockFormTemplates = List.of(formTemplate1, formTemplate2);

        when(formTemplateRepository.findAll()).thenReturn(mockFormTemplates);

        List<FormTemplate> forms = formService.getAllForms();

        assertNotNull(forms);
        assertEquals(2, forms.size());
        assertEquals("Form 1", forms.get(0).getFormName());
        assertEquals("Form 2", forms.get(1).getFormName());
        verify(formTemplateRepository, times(1)).findAll();
    }

    @Test
    void testDeleteForm() {
        String formId = "form1";

        when(formTemplateRepository.existsById(formId)).thenReturn(true);

        formService.deleteForm(formId);

        verify(formTemplateRepository, times(1)).deleteById(formId);
    }


    @Test
    void testCreateForm_InvalidInput() {
        Map<String, Object> invalidFormSchema = null;

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm("", invalidFormSchema);  // Empty form name
        });

        assertEquals("Form name cannot be null or empty.", thrown.getMessage());
    }
}
