package com.Myprojects.Dynamic_Form_Builder.entity;

import com.Myprojects.Dynamic_Form_Builder.util.Base62;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
public class FormTemplate {

    @Id
    @Column(length = 16, unique = true, nullable = false)
    private String id; //  Base62 ID

    @Column(nullable = false, unique = true)
    private String formName;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String formSchema;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private String updatedBy;


    @Transient
    private ObjectMapper objectMapper = new ObjectMapper();

    @PrePersist
    public void onCreate() {
        if (this.id == null) {
            this.id = Base62.generateUniqueId();
        }
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormSchema() {
        return formSchema;
    }

    public void setFormSchema(String formSchema) {
        this.formSchema = formSchema;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Map<String, Object> getFormSchemaAsMap() throws IOException {
        return objectMapper.readValue(this.formSchema, Map.class);
    }

    public void setFormSchemaFromMap(Map<String, Object> formSchemaMap) throws IOException {
        this.formSchema = objectMapper.writeValueAsString(formSchemaMap);
    }
}
