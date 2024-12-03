package com.Myprojects.Dynamic_Form_Builder.entity;

import com.Myprojects.Dynamic_Form_Builder.util.Base62;
import com.Myprojects.Dynamic_Form_Builder.util.JsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Getter
@Setter
public class FormSubmission {

    @Id
    @Column(length = 16, unique = true, nullable = false)
    private String id; //  Base62 ID

    @ManyToOne
    @JoinColumn(name = "form_template_id", nullable = false)
    private FormTemplate formTemplate;

    @Convert(converter = JsonConverter.class)
    @Column(nullable = false, columnDefinition = "TEXT")
    private Map<String, Object> submissionData;

    @Column(nullable = false, updatable = false)
    private LocalDateTime submittedAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private String updatedBy;

    @Column(nullable = false)
    private String submittedBy;

    @PrePersist
    public void onCreate() {
        if (this.id == null) {
            this.id = Base62.generateUniqueId();
        }
        this.submittedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }


    public Map<String, Object> getSubmissionData() {
        return submissionData;
    }

    public void setSubmissionData(Map<String, Object> submissionData) {
        this.submissionData = submissionData;
    }

    public FormTemplate getFormTemplate() {
        return formTemplate;
    }

    public void setFormTemplate(FormTemplate formTemplate) {
        this.formTemplate = formTemplate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
