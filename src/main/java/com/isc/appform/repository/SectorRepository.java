package com.isc.appform.repository;

import com.isc.appform.domain.Sector;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SectorRepository extends CrudRepository<Sector, Long> {
    @Query("select distinct s from Sector s " +
            "left join fetch s.parent")
    List<Sector> findAllWithPrefetch();
}
