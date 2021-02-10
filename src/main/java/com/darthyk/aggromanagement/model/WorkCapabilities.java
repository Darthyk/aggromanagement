package com.darthyk.aggromanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class WorkCapabilities {
    private String workType;
    private String vehicle;
    private String trailer;
    private double productionRate;
}
