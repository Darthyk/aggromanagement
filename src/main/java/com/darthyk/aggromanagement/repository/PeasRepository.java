package com.darthyk.aggromanagement.repository;

import com.darthyk.aggromanagement.model.entity.Peas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeasRepository extends JpaRepository<Peas, Integer> {
}
