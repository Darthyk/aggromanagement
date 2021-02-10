package com.darthyk.aggromanagement.repository;

import com.darthyk.aggromanagement.model.entity.Trailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrailerRepository extends JpaRepository<Trailer, Integer> {
}
