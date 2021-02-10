package com.darthyk.aggromanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ComparedEquipment {
    private Equipment equipment;
    private double production;
    private WorkCapabilities workCapability;
}
