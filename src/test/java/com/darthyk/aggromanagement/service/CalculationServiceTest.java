package com.darthyk.aggromanagement.service;

import com.darthyk.aggromanagement.model.Equipment;
import com.darthyk.aggromanagement.model.WorkCapabilities;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CalculationServiceTest {
    @Test
    public void test() {
        WorkCapabilities capability = WorkCapabilities.builder()
                .productionRate(50)
                .vehicle("vehicle1")
                .workType("worktype1")
                .build();
        WorkCapabilities capability2 = WorkCapabilities.builder()
                .productionRate(25)
                .vehicle("vehicle2")
                .workType("worktype1")
                .build();

        Equipment equipment = Equipment.builder()
                .name("name1")
                .workTypes(Arrays.asList("worktype1"))
                .capabilities(Arrays.asList(capability, capability2))
                .build();

        WorkCapabilities capability4 = WorkCapabilities.builder()
                .productionRate(25)
                .vehicle("vehicle1")
                .workType("worktype1")
                .build();
        WorkCapabilities capability5 = WorkCapabilities.builder()
                .productionRate(50)
                .vehicle("vehicle2")
                .workType("worktype1")
                .build();

        Equipment equipment2 = Equipment.builder()
                .name("name2")
                .workTypes(Arrays.asList("worktype1"))
                .capabilities(Arrays.asList(capability4, capability5))
                .build();

        WorkCapabilities capability3 = WorkCapabilities.builder()
                .productionRate(20)
                .vehicle("vehicle1")
                .workType("worktype1")
                .build();
        WorkCapabilities capability6 = WorkCapabilities.builder()
                .productionRate(40)
                .vehicle("vehicle2")
                .workType("worktype1")
                .build();

        Equipment equipment3 = Equipment.builder()
                .name("name3")
                .workTypes(Arrays.asList("worktype1"))
                .capabilities(Arrays.asList(capability3, capability6))
                .build();

        WorkCapabilities capability8 = WorkCapabilities.builder()
                .productionRate(20)
                .vehicle("vehicle1")
                .workType("worktype2")
                .build();
        WorkCapabilities capability9 = WorkCapabilities.builder()
                .productionRate(40)
                .vehicle("vehicle2")
                .workType("worktype2")
                .build();

        Equipment equipment4 = Equipment.builder()
                .name("name4")
                .workTypes(Arrays.asList("worktype2"))
                .capabilities(Arrays.asList(capability8, capability9))
                .build();

        //List<WorkCapabilities> equipment1 = CalculationService.neededEquipment(Arrays.asList(equipment, equipment2, equipment3, equipment4), 60, "worktype1");

        System.out.println();
    }
}
