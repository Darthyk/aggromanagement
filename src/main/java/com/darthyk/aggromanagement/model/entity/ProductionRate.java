package com.darthyk.aggromanagement.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "production_rate")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductionRate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_rate_id_seq")
    @SequenceGenerator(name = "production_rate_id_seq", sequenceName = "production_rate_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "trailer_type")
    private String trailerType;

    @Column(name = "work_type")
    private String workType;

    @Column(name = "production_rate_for_day")
    private Double productionRateForDay;
}
