package com.Myprojects.Dynamic_Form_Builder;

import com.Myprojects.Dynamic_Form_Builder.controller.FormTemplateController;
import com.Myprojects.Dynamic_Form_Builder.dto.FormDTO;
import com.Myprojects.Dynamic_Form_Builder.dto.SubmissionDTO;
import com.Myprojects.Dynamic_Form_Builder.entity.FormTemplate;
import com.Myprojects.Dynamic_Form_Builder.entity.FormSubmission;
import com.Myprojects.Dynamic_Form_Builder.service.EmailService;
import com.Myprojects.Dynamic_Form_Builder.service.FormService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FormTemplateControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FormService formService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private FormTemplateController formTemplateController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(formTemplateController).build();
        objectMapper = new ObjectMapper();
    }


    @Test
    void testCreateFormTemplate_FormNameEmpty() throws Exception {
        FormDTO formDTO = new FormDTO();
        formDTO.setFormName("");
        formDTO.setFormSchema(Map.of("field1", "text"));

        mockMvc.perform(post("/api/forms")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(formDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Form name cannot be null or empty."));

        verify(emailService, times(1)).sendErrorNotification(eq("Form name cannot be null or empty."), eq(""));
    }


    @Test
    void testGetFormTemplate_NotFound() throws Exception {
        when(formService.getFormById("123")).thenThrow(new RuntimeException("Form not found"));

        mockMvc.perform(get("/api/forms/123"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Form not found with ID: 123"));

        verify(emailService, times(1)).sendErrorNotification(eq("Form not found with ID: 123"), eq(""));
    }


    @Test
    void testDeleteForm_Success() throws Exception {
        doNothing().when(formService).deleteForm("123");

        mockMvc.perform(delete("/api/forms/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Form template deleted successfully."));

        verify(formService, times(1)).deleteForm("123");
    }

    @Test
    void testSubmitForm_Success() throws Exception {
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setSubmissionData(Map.of("field1", "value1"));

        FormSubmission formSubmission = new FormSubmission();
        formSubmission.setId("456");
        formSubmission.setSubmissionData(Map.of("field1", "value1"));

        when(formService.submitForm(eq("123"), anyMap())).thenReturn(formSubmission);

        mockMvc.perform(post("/api/forms/123/submissions")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(submissionDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("456"));

        verify(formService, times(1)).submitForm(eq("123"), anyMap());
    }

    @Test
    void testGetSubmissions() throws Exception {
        FormSubmission formSubmission = new FormSubmission();
        formSubmission.setId("456");
        formSubmission.setSubmissionData(Map.of("field1", "value1"));

        when(formService.getSubmissions("123")).thenReturn(List.of(formSubmission));

        mockMvc.perform(get("/api/forms/123/submissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("456"));

        verify(formService, times(1)).getSubmissions("123");
    }
}
