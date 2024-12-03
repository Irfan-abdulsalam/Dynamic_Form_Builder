package com.Myprojects.Dynamic_Form_Builder.repository;

import com.Myprojects.Dynamic_Form_Builder.entity.FormTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormTemplateRepository extends JpaRepository<FormTemplate, String> {

    /**
     * Finds all form templates by a specific form name.
     *
     * @param formName the name of the form to search for.
     * @return a list of form templates with the specified form name.
     */
    List<FormTemplate> findByFormName(String formName);

    /**
     * Checks if a form template with the given form name exists.
     *
     * @param formName the name of the form to check.
     * @return true if a form with the given name exists, false otherwise.
     */
    boolean existsByFormName(String formName);

    /**
     * Deletes all form templates by a specific form name.
     *
     * @param formName the name of the forms to delete.
     */
    void deleteByFormName(String formName);
}
