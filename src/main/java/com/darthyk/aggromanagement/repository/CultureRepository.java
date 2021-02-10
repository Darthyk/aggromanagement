package com.darthyk.aggromanagement.repository;

import com.darthyk.aggromanagement.model.entity.Culture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CultureRepository extends JpaRepository<Culture, Integer> {
}
