package com.darthyk.aggromanagement.repository;

import com.darthyk.aggromanagement.model.entity.WorkshiftNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkshiftNumberRepository extends JpaRepository<WorkshiftNumber, Integer> {
}
