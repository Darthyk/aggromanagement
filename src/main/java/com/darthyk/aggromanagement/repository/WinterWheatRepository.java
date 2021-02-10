package com.darthyk.aggromanagement.repository;

import com.darthyk.aggromanagement.model.entity.WinterWheat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WinterWheatRepository extends JpaRepository<WinterWheat, Integer> {
}
