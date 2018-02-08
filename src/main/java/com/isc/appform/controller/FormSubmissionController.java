package com.isc.appform.controller;

import com.isc.appform.domain.FormSubmission;
import com.isc.appform.domain.Sector;
import com.isc.appform.dto.FormSubmissionDTO;
import com.isc.appform.exception.InvalidFormSubmissionException;
import com.isc.appform.repository.FormSubmissionRepository;
import com.isc.appform.validation.FormSubmissionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/form-submissions")
public class FormSubmissionController {
    private final Logger logger = LoggerFactory.getLogger(FormSubmissionController.class);
    private final FormSubmissionRepository repository;
    private final EntityManager entityManager;
    private final FormSubmissionValidator validator;

    @Autowired
    public FormSubmissionController(FormSubmissionRepository repository,
                                    EntityManager entityManager,
                                    FormSubmissionValidator validator) {
        this.repository = repository;
        this.entityManager = entityManager;
        this.validator = validator;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public FormSubmissionDTO getBlankForm() {
        FormSubmissionDTO dto = new FormSubmissionDTO();
        dto.setName("");

        return dto;
    }

    /**
     * @param username - user name to create or load up
     * @return new or last-saved form submission object
     */
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public FormSubmissionDTO getForm(@PathVariable("username") String username) {
        logger.debug("Fetching form submission for " + username);
        FormSubmissionDTO dto = new FormSubmissionDTO();

        repository.findOneWithPrefetch(username).ifPresent(formSubmission -> {
            logger.debug("Found existing record for " + username);
            dto.setName(formSubmission.getName());
            formSubmission.getSelectedSectors().forEach(sector -> dto.getSelectedSectorIds().add(sector.getId()));
        });

        if (dto.getName() == null) {
            logger.debug("Form submission for user " + username + " was not found, creating new one.");
            dto.setName(username);
        }

        return dto;
    }

    /**
     * @param formSubmissionDTO - input form with name filled
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void saveForm(@RequestBody FormSubmissionDTO formSubmissionDTO) {
        if (!validator.isValid(formSubmissionDTO)) {
            throw new InvalidFormSubmissionException(formSubmissionDTO);
        }

        logger.debug("Saving Form Submission for " + formSubmissionDTO.getName() + ": " + formSubmissionDTO.toString());
        FormSubmission formSubmission = repository.findOneWithPrefetch(formSubmissionDTO.getName()).orElse(new FormSubmission());

        if (formSubmission.getName() == null) {
            logger.debug("Form submission for user " + formSubmissionDTO.getName() + " was not found in database, creating new one.");
        }

        formSubmission.setName(formSubmissionDTO.getName());
        formSubmission.getSelectedSectors().clear();

        for (Long sectorId : formSubmissionDTO.getSelectedSectorIds()) {
            logger.debug("Linking sector #" + sectorId + " to user " + formSubmissionDTO.getName());
            formSubmission.getSelectedSectors().add(entityManager.getReference(Sector.class, sectorId));
        }

        repository.save(formSubmission);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public void entityNotFoundExceptionHandler(EntityNotFoundException exception) {
        logger.error("Failed to fetch linked entity: ", exception);
    }

    @ExceptionHandler(InvalidFormSubmissionException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public void invalidFormSubmissionExceptionHandler(InvalidFormSubmissionException exception) {
        logger.error("Invalid form submission: ", exception);
    }
}
