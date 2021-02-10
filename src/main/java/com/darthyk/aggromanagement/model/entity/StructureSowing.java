package com.darthyk.aggromanagement.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "structure_sowing")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StructureSowing {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "structure_sowing_id_seq")
    @SequenceGenerator(name = "structure_sowing_id_seq", sequenceName = "structure_sowing_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "field_number")
    private String fieldNumber;

    @Column(name = "square")
    private Integer square;

    @Column(name = "culture")
    private String culture;
}
