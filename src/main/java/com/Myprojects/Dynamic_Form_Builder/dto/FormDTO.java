package com.Myprojects.Dynamic_Form_Builder.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormDTO {

    @NotBlank(message = "Form name cannot be blank")
    private String formName;

    @NotNull(message = "Form schema cannot be null")
    private Map<String, Object> formSchema;

    public String getFormName() {
        return formName;
    }


    public void setFormName(String formName) {
        this.formName = formName;
    }


    public Map<String, Object> getFormSchema() {
        return formSchema;
    }

    public void setFormSchema(Map<String, Object> formSchema) {
        this.formSchema = formSchema;
    }
}
