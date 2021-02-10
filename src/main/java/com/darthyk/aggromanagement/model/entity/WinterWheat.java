package com.darthyk.aggromanagement.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "winter_wheat")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WinterWheat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "winter_wheat_id_seq")
    @SequenceGenerator(name = "winter_wheat_id_seq", sequenceName = "winter_wheat_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "work_type")
    private String workType;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;
}
