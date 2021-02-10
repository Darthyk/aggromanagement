package com.darthyk.aggromanagement.repository;

import com.darthyk.aggromanagement.model.entity.ProductionRate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionRateRepository extends JpaRepository<ProductionRate, Integer> {
    List<ProductionRate> getAllByTrailerType(String trailerType);
}
