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
@Table(name = "workshift_number")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkshiftNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workshift_number_id_seq")
    @SequenceGenerator(name = "workshift_number_id_seq", sequenceName = "workshift_number_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "work_type")
    private String workType;

    @Column(name = "workshift_number")
    private Integer workshiftNumber;
}
