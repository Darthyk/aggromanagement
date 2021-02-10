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
@Table(name = "trailer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trailer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trailer_id_seq")
    @SequenceGenerator(name = "trailer_id_seq", sequenceName = "trailer_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "trailer_name")
    private String trailerName;

    @Column(name = "trailer_type")
    private String trailerType;
}
