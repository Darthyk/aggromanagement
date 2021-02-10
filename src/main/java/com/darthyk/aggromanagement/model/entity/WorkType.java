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
@Table(name = "work_type")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "work_type_id_seq")
    @SequenceGenerator(name = "work_type_id_seq", sequenceName = "work_type_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name")
    private String name;
}
