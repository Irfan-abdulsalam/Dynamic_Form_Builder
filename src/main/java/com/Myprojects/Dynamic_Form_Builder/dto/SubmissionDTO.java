package com.Myprojects.Dynamic_Form_Builder.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmissionDTO {

    @NotNull(message = "Submission data cannot be null or empty.")
    private Map<String, Object> submissionData;


    public @NotNull(message = "Submission data cannot be null or empty.") Map<String, Object> getSubmissionData() {
        return submissionData;
    }

    public void setSubmissionData(@NotNull(message = "Submission data cannot be null or empty.") Map<String, Object> submissionData) {
        this.submissionData = submissionData;
    }
}
