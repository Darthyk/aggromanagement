package com.darthyk.aggromanagement.repository;

import com.darthyk.aggromanagement.model.entity.SugarBeet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SugarBeetRepository extends JpaRepository<SugarBeet, Integer> {
}
