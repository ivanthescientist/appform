package com.isc.appform.repository;

import com.isc.appform.domain.FormSubmission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FormSubmissionRepository extends CrudRepository<FormSubmission, Long> {
    @Query(value = "select fs from FormSubmission fs " +
            "left join fetch fs.selectedSectors ss " +
            "where fs.name=?1")
    Optional<FormSubmission> findOneWithPrefetch(String name);

    @Query(value = "select distinct fs from FormSubmission fs " +
            "left join fetch fs.selectedSectors ss")
    List<FormSubmission> findAllWithPrefetch();
}
