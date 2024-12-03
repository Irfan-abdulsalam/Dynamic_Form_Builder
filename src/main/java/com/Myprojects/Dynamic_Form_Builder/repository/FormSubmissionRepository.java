package com.Myprojects.Dynamic_Form_Builder.repository;

import com.Myprojects.Dynamic_Form_Builder.entity.FormSubmission;
import com.Myprojects.Dynamic_Form_Builder.entity.FormTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormSubmissionRepository extends JpaRepository<FormSubmission, String> {

    /**
     * Retrieves all submissions associated with the given form template.
     *
     * @param formTemplate the form template to filter submissions by.
     * @return a list of form submissions for the specified template.
     */
    List<FormSubmission> findByFormTemplate(FormTemplate formTemplate);

    /**
     * Counts the total number of submissions for a given form template.
     *
     * @param formTemplate the form template to count submissions for.
     * @return the number of submissions for the specified template.
     */
    long countByFormTemplate(FormTemplate formTemplate);

    /**
     * Deletes all submissions associated with the given form template.
     *
     * @param formTemplate the form template whose submissions should be deleted.
     */
    void deleteByFormTemplate(FormTemplate formTemplate);
}
