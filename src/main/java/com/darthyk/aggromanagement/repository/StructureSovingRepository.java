package com.darthyk.aggromanagement.repository;

import com.darthyk.aggromanagement.model.entity.StructureSowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StructureSovingRepository extends JpaRepository<StructureSowing, Integer> {
}
