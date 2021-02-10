package com.darthyk.aggromanagement.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsolidatedData {
    private String cultureName;
    private String workType;
    private LocalDate startDate;
    private LocalDate endDate;
    private int workShiftPerDay;
    private int workshifts;
    private int totalSquare;
    private WorkStatus workStatus;
    List<Map.Entry<Equipment, String>> reservedEquipment;
}
