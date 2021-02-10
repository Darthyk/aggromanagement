package com.darthyk.aggromanagement.repository;

import com.darthyk.aggromanagement.model.entity.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkTypeRepository extends JpaRepository<WorkType, Integer> {
}
