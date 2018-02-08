package com.isc.appform.validation;

import com.isc.appform.dto.FormSubmissionDTO;
import org.springframework.stereotype.Component;

@Component
public class FormSubmissionValidator {
    public boolean isValid(FormSubmissionDTO submissionDTO) {
        if (submissionDTO.getName() == null || submissionDTO.getName().length() < 1) {
            return false;
        }

        if (submissionDTO.getSelectedSectorIds() == null || submissionDTO.getSelectedSectorIds().size() < 1) {
            return false;
        }

        return true;
    }
}
