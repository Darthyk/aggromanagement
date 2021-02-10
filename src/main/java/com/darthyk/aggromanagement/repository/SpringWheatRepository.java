package com.darthyk.aggromanagement.repository;

import com.darthyk.aggromanagement.model.entity.SpringWheat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringWheatRepository extends JpaRepository<SpringWheat, Integer> {
}
