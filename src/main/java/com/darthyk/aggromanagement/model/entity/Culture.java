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
@Table(name = "culture")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Culture {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "culture_id_seq")
    @SequenceGenerator(name = "culture_id_seq", sequenceName = "culture_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name")
    private String name;
}
