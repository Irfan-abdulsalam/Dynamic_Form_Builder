package com.Myprojects.Dynamic_Form_Builder.service;

import com.Myprojects.Dynamic_Form_Builder.entity.FormTemplate;
import com.Myprojects.Dynamic_Form_Builder.entity.FormSubmission;
import com.Myprojects.Dynamic_Form_Builder.repository.FormTemplateRepository;
import com.Myprojects.Dynamic_Form_Builder.repository.FormSubmissionRepository;
import com.Myprojects.Dynamic_Form_Builder.util.JsonValidationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class FormService {

    @Autowired
    private FormTemplateRepository formTemplateRepository;

    @Autowired
    private FormSubmissionRepository formSubmissionRepository;

    public FormTemplate createForm(String formName, Map<String, Object> formSchema) {
        validateString(formName, "Form name cannot be null or empty.");
        if (formSchema == null || formSchema.isEmpty()) {
            throw new IllegalArgumentException("Form schema cannot be null or empty.");
        }

        FormTemplate formTemplate = new FormTemplate();
        formTemplate.setFormName(formName);

        //JSON String
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String formSchemaJson = objectMapper.writeValueAsString(formSchema);
            formTemplate.setFormSchema(formSchemaJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing form schema: " + e.getMessage(), e);
        }

        formTemplate.setCreatedBy("user"); // Example, adjust as per your context
        formTemplate.setCreatedAt(LocalDateTime.now());

        return formTemplateRepository.save(formTemplate);
    }

    // Retrieve all form templates
    public List<FormTemplate> getAllForms() {
        return formTemplateRepository.findAll();
    }

    // Retrieve a 1 form template by ID
    public FormTemplate getFormById(String id) {
        validateString(id, "Form ID cannot be null or empty.");
        return formTemplateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Form not found with ID: " + id));
    }

    public FormSubmission submitForm(String formTemplateId, Map<String, Object> submissionData) {
        validateString(formTemplateId, "Form template ID cannot be null or empty.");

        if (submissionData == null || submissionData.isEmpty()) {
            throw new IllegalArgumentException("Submission data cannot be null or empty.");
        }

        FormTemplate formTemplate = getFormById(formTemplateId);

        try {
            JsonValidationUtil.validateJson(formTemplate.getFormSchema(), submissionData);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid submission data: " + e.getMessage());
        }

        FormSubmission submission = new FormSubmission();
        submission.setFormTemplate(formTemplate);
        submission.setSubmissionData(submissionData);
        submission.setSubmittedBy(getLoggedInUsername());
        submission.setSubmittedAt(LocalDateTime.now());

        return formSubmissionRepository.save(submission);
    }


    // Retrieve submissions for a specific form template
    public List<FormSubmission> getSubmissions(String formTemplateId) {
        validateString(formTemplateId, "Form template ID cannot be null or empty.");
        FormTemplate formTemplate = getFormById(formTemplateId);
        return formSubmissionRepository.findByFormTemplate(formTemplate);
    }

    // Update a specific form submission
    public FormSubmission updateSubmission(String submissionId, Map<String, Object> submissionData) {
        validateString(submissionId, "Submission ID cannot be null or empty.");

        if (submissionData == null || submissionData.isEmpty()) {
            throw new IllegalArgumentException("Submission data cannot be null or empty.");
        }

        FormSubmission existingSubmission = formSubmissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        FormTemplate formTemplate = existingSubmission.getFormTemplate();

        try {
            JsonValidationUtil.validateJson(formTemplate.getFormSchema(), submissionData);
            existingSubmission.setSubmissionData(submissionData);
            existingSubmission.setUpdatedBy(getLoggedInUsername());
            existingSubmission.setUpdatedAt(LocalDateTime.now());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid submission data: " + e.getMessage());
        }

        return formSubmissionRepository.save(existingSubmission);
    }

    // Delete a specific form submission
    public void deleteSubmission(String submissionId) {
        validateString(submissionId, "Submission ID cannot be null or empty.");
        FormSubmission formSubmission = formSubmissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        formSubmissionRepository.delete(formSubmission);
    }

    // Update the form
    public FormTemplate updateForm(String id, String formName, Map<String, Object> formSchema) {
        validateString(id, "Form ID cannot be null or empty.");
        validateString(formName, "Form name cannot be null or empty.");
        if (formSchema == null || formSchema.isEmpty()) {
            throw new IllegalArgumentException("Form schema cannot be null or empty.");
        }

        FormTemplate existingTemplate = getFormById(id);

        try {
            JsonValidationUtil.validateJson(existingTemplate.getFormSchema(), formSchema);  // This now uses the Map directly

            existingTemplate.setFormName(formName);

            ObjectMapper objectMapper = new ObjectMapper();
            String formSchemaJson = objectMapper.writeValueAsString(formSchema);
            existingTemplate.setFormSchema(formSchemaJson);

            existingTemplate.setUpdatedBy(getLoggedInUsername());
            existingTemplate.setUpdatedAt(LocalDateTime.now());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error updating form schema: " + e.getMessage());
        }

        return formTemplateRepository.save(existingTemplate);
    }



    // Delete a specific form template
    public void deleteForm(String id) {
        validateString(id, "Form ID cannot be null or empty.");
        if (!formTemplateRepository.existsById(id)) {
            throw new IllegalArgumentException("Form not found with ID: " + id);
        }
        formTemplateRepository.deleteById(id);
    }

    private void validateString(String str, String errorMessage) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private String getLoggedInUsername() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return user.getUsername();
        } catch (Exception e) {
            return "system"; // Default value for system or test cases
        }
    }
}
