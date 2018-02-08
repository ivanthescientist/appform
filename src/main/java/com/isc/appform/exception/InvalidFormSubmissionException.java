package com.isc.appform.exception;

import com.isc.appform.dto.FormSubmissionDTO;

public class InvalidFormSubmissionException extends RuntimeException {
    public InvalidFormSubmissionException(FormSubmissionDTO submissionDTO) {
        super(submissionDTO.toString());
    }
}
