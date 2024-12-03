package com.Myprojects.Dynamic_Form_Builder.controller;

import com.Myprojects.Dynamic_Form_Builder.dto.FormDTO;
import com.Myprojects.Dynamic_Form_Builder.dto.SubmissionDTO;
import com.Myprojects.Dynamic_Form_Builder.entity.FormTemplate;
import com.Myprojects.Dynamic_Form_Builder.entity.FormSubmission;
import com.Myprojects.Dynamic_Form_Builder.model.ApiResponse;
import com.Myprojects.Dynamic_Form_Builder.service.EmailService;
import com.Myprojects.Dynamic_Form_Builder.service.FormService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/forms")
public class FormTemplateController {

    @Autowired
    private FormService formService;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<?> createFormTemplate(@Valid @RequestBody FormDTO formDTO) {
        try {
            if (formDTO.getFormName() == null || formDTO.getFormName().isEmpty()) {
                emailService.sendErrorNotification("Form name cannot be null or empty.", "");
                return ResponseEntity.badRequest().body(new ApiResponse("error", "Form name cannot be null or empty."));
            }

            // Deserialize
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> formSchemaMap = objectMapper.convertValue(formDTO.getFormSchema(), Map.class);

            FormTemplate savedTemplate = formService.createForm(formDTO.getFormName(), formSchemaMap);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTemplate);
        } catch (IllegalArgumentException e) {
            emailService.sendErrorNotification(e.getMessage(), "");
            return ResponseEntity.badRequest().body(new ApiResponse("error", e.getMessage()));
        } catch (Exception e) {
            emailService.sendErrorNotification("An error occurred while creating the form template.", "");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "An error occurred while creating the form template."));
        }
    }

    @GetMapping
    public ResponseEntity<List<FormTemplate>> getAllForms() {
        List<FormTemplate> forms = formService.getAllForms();
        return ResponseEntity.ok(forms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFormTemplate(@PathVariable String id) {
        try {
            FormTemplate formTemplate = formService.getFormById(id);
            return ResponseEntity.ok(formTemplate);
        } catch (RuntimeException e) {
            emailService.sendErrorNotification("Form not found with ID: " + id, "");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error", "Form not found with ID: " + id));
        } catch (Exception e) {
            emailService.sendErrorNotification("An error occurred while retrieving the form template.", "");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "An error occurred while retrieving the form template."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFormTemplate(@PathVariable String id, @Valid @RequestBody FormDTO formDTO) {
        try {
            if (formDTO.getFormName() == null || formDTO.getFormName().isEmpty()) {
                emailService.sendErrorNotification("Form name cannot be null or empty.", "");
                return ResponseEntity.badRequest().body(new ApiResponse("error", "Form name cannot be null or empty."));
            }

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> formSchemaMap = objectMapper.convertValue(formDTO.getFormSchema(), Map.class);

            FormTemplate updatedTemplate = formService.updateForm(id, formDTO.getFormName(), formSchemaMap);
            return ResponseEntity.ok(new ApiResponse("success", "Form template updated successfully.", updatedTemplate));
        } catch (IllegalArgumentException e) {
            emailService.sendErrorNotification(e.getMessage(), "");
            return ResponseEntity.badRequest().body(new ApiResponse("error", e.getMessage()));
        } catch (Exception e) {
            emailService.sendErrorNotification("An error occurred while updating the form template.", "");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "An error occurred while updating the form template."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteForm(@PathVariable String id) {
        try {
            formService.deleteForm(id);
            return ResponseEntity.ok(new ApiResponse("success", "Form template deleted successfully."));
        } catch (RuntimeException e) {
            emailService.sendErrorNotification("Form not found with ID: " + id, "");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error", "Form not found with ID: " + id));
        } catch (Exception e) {
            emailService.sendErrorNotification("An error occurred while deleting the form template.", "");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "An error occurred while deleting the form template."));
        }
    }

    @PostMapping("/{formId}/submissions")
    public ResponseEntity<?> submitForm(@PathVariable String formId, @Valid @RequestBody SubmissionDTO submissionDTO) {
        try {
            if (submissionDTO.getSubmissionData() == null || submissionDTO.getSubmissionData().isEmpty()) {
                emailService.sendErrorNotification("Submission data cannot be null or empty.", "");
                return ResponseEntity.badRequest().body(new ApiResponse("error", "Submission data cannot be null or empty."));
            }

            FormSubmission submission = formService.submitForm(formId, submissionDTO.getSubmissionData());
            return ResponseEntity.status(HttpStatus.CREATED).body(submission);
        } catch (IllegalArgumentException e) {
            emailService.sendErrorNotification(e.getMessage(), "");
            return ResponseEntity.badRequest().body(new ApiResponse("error", e.getMessage()));
        } catch (Exception e) {
            emailService.sendErrorNotification("An error occurred while submitting the form.", "");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "An error occurred while submitting the form."));
        }
    }

    @GetMapping("/{formId}/submissions")
    public ResponseEntity<?> getSubmissions(@PathVariable String formId) {
        try {
            List<FormSubmission> submissions = formService.getSubmissions(formId);
            return ResponseEntity.ok(submissions);
        } catch (RuntimeException e) {
            emailService.sendErrorNotification("No submissions found for Form ID: " + formId, "");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error", "No submissions found for Form ID: " + formId));
        } catch (Exception e) {
            emailService.sendErrorNotification("An error occurred while retrieving form submissions.", "");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "An error occurred while retrieving form submissions."));
        }
    }

    @PutMapping("/{formId}/submissions/{submissionId}")
    public ResponseEntity<?> updateFormSubmission(
            @PathVariable String formId,
            @PathVariable String submissionId,
            @Valid @RequestBody SubmissionDTO submissionDTO
    ) {
        try {
            if (submissionDTO.getSubmissionData() == null || submissionDTO.getSubmissionData().isEmpty()) {
                emailService.sendErrorNotification("Submission data cannot be null or empty.", "");
                return ResponseEntity.badRequest().body(new ApiResponse("error", "Submission data cannot be null or empty."));
            }

            FormSubmission updatedSubmission = formService.updateSubmission(submissionId, submissionDTO.getSubmissionData());
            return ResponseEntity.ok(new ApiResponse("success", "Form submission updated successfully.", updatedSubmission));
        } catch (IllegalArgumentException e) {
            emailService.sendErrorNotification(e.getMessage(), "");
            return ResponseEntity.badRequest().body(new ApiResponse("error", e.getMessage()));
        } catch (RuntimeException e) {
            emailService.sendErrorNotification("Submission not found with ID: " + submissionId, "");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error", "Submission not found with ID: " + submissionId));
        } catch (Exception e) {
            emailService.sendErrorNotification("An error occurred while updating the form submission.", "");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "An error occurred while updating the form submission."));
        }
    }

    @DeleteMapping("vn")
    public ResponseEntity<?> deleteSubmission(@PathVariable String formId, @PathVariable String submissionId) {
        try {
            formService.deleteSubmission(submissionId);
            return ResponseEntity.ok(new ApiResponse("success", "Form submission deleted successfully."));
        } catch (RuntimeException e) {
            emailService.sendErrorNotification("Submission not found with ID: " + submissionId, "");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error", "Submission not found with ID: " + submissionId));
        } catch (Exception e) {
            emailService.sendErrorNotification("An error occurred while deleting the form submission.", "");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "An error occurred while deleting the form submission."));
        }
    }
}
