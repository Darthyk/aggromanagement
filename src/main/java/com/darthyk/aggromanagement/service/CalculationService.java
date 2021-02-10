package com.darthyk.aggromanagement.service;

import com.darthyk.aggromanagement.model.ComparedEquipment;
import com.darthyk.aggromanagement.model.ConsolidatedData;
import com.darthyk.aggromanagement.model.Equipment;
import com.darthyk.aggromanagement.model.WorkCapabilities;
import com.darthyk.aggromanagement.model.WorkStatus;
import com.darthyk.aggromanagement.model.dto.Culture;
import com.darthyk.aggromanagement.model.dto.Report;
import com.darthyk.aggromanagement.model.entity.Peas;
import com.darthyk.aggromanagement.model.entity.ProductionRate;
import com.darthyk.aggromanagement.model.entity.SpringWheat;
import com.darthyk.aggromanagement.model.entity.StructureSowing;
import com.darthyk.aggromanagement.model.entity.SugarBeet;
import com.darthyk.aggromanagement.model.entity.Trailer;
import com.darthyk.aggromanagement.model.entity.Vehicle;
import com.darthyk.aggromanagement.model.entity.WinterWheat;
import com.darthyk.aggromanagement.model.entity.WorkshiftNumber;
import com.darthyk.aggromanagement.repository.PeasRepository;
import com.darthyk.aggromanagement.repository.ProductionRateRepository;
import com.darthyk.aggromanagement.repository.SpringWheatRepository;
import com.darthyk.aggromanagement.repository.StructureSovingRepository;
import com.darthyk.aggromanagement.repository.SugarBeetRepository;
import com.darthyk.aggromanagement.repository.TrailerRepository;
import com.darthyk.aggromanagement.repository.VehicleRepository;
import com.darthyk.aggromanagement.repository.WinterWheatRepository;
import com.darthyk.aggromanagement.repository.WorkshiftNumberRepository;
import com.sun.jdi.connect.spi.TransportService;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CalculationService {
    private static final boolean GET_ALL_EQUIPMENT = true;
    private static final double DONT_NEED_TO_CHECK_PRODUCTION = 0;
    @Autowired
    private PeasRepository peasRepository;
    @Autowired
    private SugarBeetRepository sugarBeetRepository;
    @Autowired
    private SpringWheatRepository springWheatRepository;
    @Autowired
    private WinterWheatRepository winterWheatRepository;
    @Autowired
    private WorkshiftNumberRepository workshiftNumberRepository;
    @Autowired
    private StructureSovingRepository structureSovingRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private TrailerRepository trailerRepository;
    @Autowired
    private ProductionRateRepository productionRateRepository;

    public Report calcultate() {
        List<ConsolidatedData> consolidatedData = getConsolidatedData();
        List<Equipment> availableEquipment = getAvailableEquipment();
        List<String> availableVehicles = getAvailableVehicles();
        List<StructureSowing> structureSowings = structureSovingRepository.findAll();

        consolidatedData.sort(Comparator.comparing(ConsolidatedData::getStartDate));
        LocalDate earliestStartDate = consolidatedData.get(0).getStartDate();
        consolidatedData.sort(Comparator.comparing(ConsolidatedData::getEndDate));
        LocalDate latestEndDate = consolidatedData.get(consolidatedData.size()-1).getEndDate();
        consolidatedData.sort(Comparator.comparing(ConsolidatedData::getStartDate));
        long workDays = ChronoUnit.DAYS.between(earliestStartDate, latestEndDate);
        Report report = Report.builder().culture(new ArrayList<>()).build();

        LocalDate workDate = earliestStartDate;
        for (int i = 1; i <= workDays; i++) {
            LocalDate localDate = workDate;
            List<ConsolidatedData> startDateToCheck = consolidatedData.stream()
                    .filter(data -> data.getStartDate().equals(localDate))
                    .collect(Collectors.toList());
            List<ConsolidatedData> endDateToCheck = consolidatedData.stream()
                    .filter(data -> data.getEndDate().equals(localDate))
                    .collect(Collectors.toList());
            for (ConsolidatedData data : startDateToCheck) {
                if (data.getStartDate().equals(workDate)) {
                    int totalSquare = data.getTotalSquare();
                    int workshifts = data.getWorkshifts();
                    double minimalProductionRate = totalSquare/workshifts;

                    List<WorkCapabilities> equipment = neededEquipment(availableEquipment, availableVehicles,
                            minimalProductionRate, data.getWorkType(), !GET_ALL_EQUIPMENT);
                    if (!equipment.isEmpty()) {
                        Map<Equipment, String> selectedEquipment = new HashMap<>();
                        for (WorkCapabilities capabilities : equipment) {
                            Equipment selectedTrailer = availableEquipment.stream()
                                    .filter(eq -> eq.getCapabilities().contains(capabilities))
                                    .collect(Collectors.toList()).get(0);
                            selectedEquipment.put(selectedTrailer, capabilities.getVehicle());
                            availableEquipment.remove(selectedTrailer);
                            availableVehicles.remove(capabilities.getVehicle());
                        }
                        data.setWorkStatus(WorkStatus.IN_PROGRESS);
                        data.setReservedEquipment(new ArrayList<>(selectedEquipment.entrySet()));
                    } else {
                        if (availableEquipment.stream().anyMatch(e -> e.getWorkTypes().contains(data.getWorkType()))) {
                            List<WorkCapabilities> workCapabilities = neededEquipment(availableEquipment,
                                    availableVehicles, DONT_NEED_TO_CHECK_PRODUCTION, data.getWorkType(),
                                    GET_ALL_EQUIPMENT);
                            List<Map.Entry<Equipment, String>> reservedEquipment = new ArrayList<>();
                            for (WorkCapabilities workCapability : workCapabilities) {
                                Equipment selectedTrailer = availableEquipment.stream()
                                        .filter(eq -> eq.getCapabilities().contains(workCapability))
                                        .collect(Collectors.toList()).get(0);
                                reservedEquipment.add(Map.of(selectedTrailer,
                                        workCapability.getVehicle()).entrySet().stream().findFirst().get());
                                availableEquipment.remove(selectedTrailer);
                                availableVehicles.remove(workCapability.getVehicle());
                            }
                            data.setReservedEquipment(reservedEquipment);
                            data.setWorkStatus(WorkStatus.IN_PROGRESS);
                            double remainProduction = workCapabilities.stream()
                                    .mapToDouble(WorkCapabilities::getProductionRate).sum();
                            double processedSquare = remainProduction * data.getWorkshifts();
                            List<StructureSowing> processedFields = new ArrayList<>();
                            List<StructureSowing> cultureStructure = structureSowings.stream()
                                    .filter(ss -> ss.getCulture().equals(data.getCultureName()))
                                    .collect(Collectors.toList());
                            int processSquare = 0;
                            while (processSquare <= processedSquare) {
                                for (StructureSowing structureSowing : cultureStructure) {
                                    processSquare = processSquare + structureSowing.getSquare();
                                    processedFields.add(structureSowing);
                                }
                            }
                            //Calculate fields that are not processed
                            cultureStructure.removeAll(processedFields);
                            //TODO add vehicle and trailer calculation for output

                            List<Culture> culture = report.getCulture();
                            culture.add(Culture.builder()
                                    .fields(cultureStructure.stream()
                                            .map(StructureSowing::getFieldNumber)
                                            .collect(Collectors.toList()))
                                    .name(data.getCultureName())
                                    .trailers(getAvailableEquipment().stream()
                                            .map(Equipment::getName).collect(Collectors.toList()))
                                    .workType(data.getWorkType())
                                    .startDate(data.getStartDate())
                                    .endDate(data.getEndDate())
                                    .build());
                            report.setCulture(culture);

                        } else {
                            //TODO add all data to output
                            List<Culture> culture = report.getCulture();
                            culture.add(Culture.builder()
                                    .fields(structureSowings.stream()
                                            .map(StructureSowing::getFieldNumber)
                                            .collect(Collectors.toList()))
                                    .name(data.getCultureName())
                                    .trailers(getAvailableEquipment().stream()
                                            .map(Equipment::getName).collect(Collectors.toList()))
                                    .workType(data.getWorkType())
                                    .startDate(data.getStartDate())
                                    .endDate(data.getEndDate())
                                    .build());
                            report.setCulture(culture);
                        }
                    }
                }
            }

            for (ConsolidatedData data : endDateToCheck) {
                if (data.getEndDate().equals(workDate)) {
                    for (Map.Entry<Equipment, String> equipmentStringEntry : data.getReservedEquipment()) {
                        availableEquipment.add(equipmentStringEntry.getKey());
                        availableVehicles.add(equipmentStringEntry.getValue());
                    }
                    data.setWorkStatus(WorkStatus.COMPLETED);
                }
            }
            workDate = workDate.plusDays(1);
        }
        return report;
    }

    public static List<WorkCapabilities> neededEquipment(List<Equipment> equipmentList, List<String> vehicleList,
            double neededProduction, String workType, boolean getAll) {
        List<List<ComparedEquipment>> allowedEquipment = new ArrayList<>();
        for (Equipment equipment : equipmentList) {
            List<ComparedEquipment> list = new ArrayList<>();
            for (WorkCapabilities capability : equipment.getCapabilities()) {
                if (capability.getWorkType().equals(workType) && vehicleList.contains(capability.getVehicle())) {
                    list.add(ComparedEquipment.builder()
                            .equipment(equipment)
                            .workCapability(capability)
                            .production(capability.getProductionRate()).build());
                }
            }
            if (!list.isEmpty()) {
                allowedEquipment.add(list);
            }
        }

        if (getAll) {
            List<WorkCapabilities> allAvailableEquipment = new ArrayList<>();
            for (List<ComparedEquipment> equipments : allowedEquipment) {
                allAvailableEquipment.add(equipments.stream()
                        .map(ComparedEquipment::getWorkCapability)
                        .findFirst().get());
            }
            return allAvailableEquipment;
        }

        List<ComparedEquipment> selectedMin = new ArrayList<>();
        List<ComparedEquipment> selectedMax = new ArrayList<>();
        List<ComparedEquipment> selectedAvg = new ArrayList<>();
        List[] array = allowedEquipment.toArray(new List[0]);
        for (int j = 1; j <= allowedEquipment.size(); j++) {
            EquipmentComparator comparator = new EquipmentComparator();
            ComparedEquipment minComparedEquip = (ComparedEquipment) array[j].stream().min(comparator).get();
            ComparedEquipment maxComparedEquip = (ComparedEquipment) array[j].stream().max(comparator).get();
            selectedMax.add(maxComparedEquip);
            selectedMin.add(minComparedEquip);
            if(j%2 == 0) {
                selectedAvg.add(maxComparedEquip);
            } else {
                selectedAvg.add(minComparedEquip);
            }
            if (selectedMin.stream().mapToDouble(ComparedEquipment::getProduction).sum() >= neededProduction) {
                return selectedMin.stream().map(ComparedEquipment::getWorkCapability).collect(Collectors.toList());
            } else if (selectedAvg.stream().mapToDouble(ComparedEquipment::getProduction).sum() >= neededProduction) {
                return selectedAvg.stream().map(ComparedEquipment::getWorkCapability).collect(Collectors.toList());
            } else if (selectedMax.stream().mapToDouble(ComparedEquipment::getProduction).sum() >= neededProduction){
                return selectedMax.stream().map(ComparedEquipment::getWorkCapability).collect(Collectors.toList());
            }
        }


        return new ArrayList<>();
    }

    static class EquipmentComparator implements Comparator<ComparedEquipment> {

        @Override public int compare(ComparedEquipment o1, ComparedEquipment o2) {
            if (o1.getProduction() > o2.getProduction()) {
                return 1;
            } else if (o1.getProduction() < o2.getProduction()) {
                return -1;
            } else
                return 0;
        }
    }

    private List<String> getAvailableVehicles() {
        return vehicleRepository.findAll().stream().map(Vehicle::getVehicleType).collect(Collectors.toList());
    }

    private List<Equipment> getAvailableEquipment() {
        List<Trailer> trailerList = trailerRepository.findAll();

        List<Equipment> equipment = new ArrayList<>();

        for (Trailer trailer : trailerList) {
            List<ProductionRate> productionRates = productionRateRepository.getAllByTrailerType(trailer.getTrailerType());
            List<WorkCapabilities> workCapabilities = new ArrayList<>();
            List<String> workTypes = new ArrayList<>();
            for (ProductionRate productionRate : productionRates) {
                workTypes.add(productionRate.getWorkType());
                workCapabilities.add(WorkCapabilities.builder()
                        .workType(productionRate.getWorkType())
                        .vehicle(productionRate.getVehicleType())
                        .trailer(trailer.getTrailerType())
                        .productionRate(productionRate.getProductionRateForDay())
                        .build());
            }
            equipment.add(Equipment.builder()
                    .name(trailer.getTrailerType())
                    .workTypes(workTypes)
                    .capabilities(workCapabilities)
                    .build());
        }
        return equipment;
    }

    private List<ConsolidatedData> getConsolidatedData() {
        List<Peas> peasList = peasRepository.findAll();
        List<SugarBeet> sugarBeetList = sugarBeetRepository.findAll();
        List<SpringWheat> springWheatList = springWheatRepository.findAll();
        List<WinterWheat> winterWheatList = winterWheatRepository.findAll();
        List<WorkshiftNumber> workshiftNumbers = workshiftNumberRepository.findAll();
        List<StructureSowing> structureSowingList = structureSovingRepository.findAll();

        List<ConsolidatedData> allData = new ArrayList<>();
        for (Peas peas : peasList) {
            LocalDate start = parseDate(peas.getStartDate());
            LocalDate end = parseDate(peas.getEndDate());
            int workshiftsPerDay = workshiftNumbers.stream()
                    .filter(workshiftNumber -> workshiftNumber.getWorkType().equalsIgnoreCase(peas.getWorkType()))
                    .collect(Collectors.toList())
                    .get(0)
                    .getWorkshiftNumber();
            Period period = Period.between(start, end);
            List<Map.Entry<Equipment, String>> equipment = new ArrayList<>();
            int square = structureSowingList.stream()
                    .filter(o -> o.getCulture().equalsIgnoreCase("Горох"))
                    .mapToInt(StructureSowing::getSquare)
                    .sum();

            allData.add(ConsolidatedData.builder()
                    .cultureName("Горох")
                    .workType(peas.getWorkType())
                    .startDate(start)
                    .endDate(end)
                    .workShiftPerDay(workshiftsPerDay)
                    .workshifts(period.getDays() * workshiftsPerDay)
                    .reservedEquipment(equipment)
                    .totalSquare(square)
                    .workStatus(WorkStatus.NOT_STARTED)
                    .build()
            );
        }

        for (SugarBeet sugarBeet : sugarBeetList) {
            LocalDate start = parseDate(sugarBeet.getStartDate());
            LocalDate end = parseDate(sugarBeet.getEndDate());
            int workshiftsPerDay = workshiftNumbers.stream()
                    .filter(workshiftNumber -> workshiftNumber.getWorkType().equalsIgnoreCase(sugarBeet.getWorkType()))
                    .collect(Collectors.toList())
                    .get(0)
                    .getWorkshiftNumber();
            Period period = Period.between(start, end);
            List<Map.Entry<Equipment, String>> equipment = new ArrayList<>();
            int square = structureSowingList.stream()
                    .filter(o -> o.getCulture().equalsIgnoreCase("Сахарная свекла"))
                    .mapToInt(StructureSowing::getSquare)
                    .sum();

            allData.add(ConsolidatedData.builder()
                    .cultureName("Сахарная свекла")
                    .workType(sugarBeet.getWorkType())
                    .startDate(start)
                    .endDate(end)
                    .workShiftPerDay(workshiftsPerDay)
                    .workshifts(period.getDays() * workshiftsPerDay)
                    .reservedEquipment(equipment)
                    .totalSquare(square)
                    .workStatus(WorkStatus.NOT_STARTED)
                    .build()
            );
        }

        for (SpringWheat springWheat : springWheatList) {
            LocalDate start = parseDate(springWheat.getStartDate());
            LocalDate end = parseDate(springWheat.getEndDate());
            int workshiftsPerDay = workshiftNumbers.stream()
                    .filter(workshiftNumber -> workshiftNumber.getWorkType().trim().equalsIgnoreCase(springWheat.getWorkType().trim()))
                    .collect(Collectors.toList())
                    .get(0)
                    .getWorkshiftNumber();
            Period period = Period.between(start, end);
            List<Map.Entry<Equipment, String>> equipment = new ArrayList<>();
            int square = structureSowingList.stream()
                    .filter(o -> o.getCulture().equalsIgnoreCase("Яровая пшеница"))
                    .mapToInt(StructureSowing::getSquare)
                    .sum();

            allData.add(ConsolidatedData.builder()
                    .cultureName("Яровая пшеница")
                    .workType(springWheat.getWorkType())
                    .startDate(start)
                    .endDate(end)
                    .workShiftPerDay(workshiftsPerDay)
                    .workshifts(period.getDays() * workshiftsPerDay)
                    .reservedEquipment(equipment)
                    .totalSquare(square)
                    .workStatus(WorkStatus.NOT_STARTED)
                    .build()
            );
        }

        for (WinterWheat winterWheat : winterWheatList) {
            LocalDate start = parseDate(winterWheat.getStartDate());
            LocalDate end = parseDate(winterWheat.getEndDate());
            int workshiftsPerDay = workshiftNumbers.stream()
                    .filter(workshiftNumber -> workshiftNumber.getWorkType().trim().equalsIgnoreCase(winterWheat.getWorkType().trim()))
                    .collect(Collectors.toList())
                    .get(0)
                    .getWorkshiftNumber();
            Period period = Period.between(start, end);
            List<Map.Entry<Equipment, String>> equipment = new ArrayList<>();
            int square = structureSowingList.stream()
                    .filter(o -> o.getCulture().equalsIgnoreCase("Озимая пшеница"))
                    .mapToInt(StructureSowing::getSquare)
                    .sum();

            allData.add(ConsolidatedData.builder()
                    .cultureName("Озимая пшеница")
                    .workType(winterWheat.getWorkType())
                    .startDate(start)
                    .endDate(end)
                    .workShiftPerDay(workshiftsPerDay)
                    .workshifts(period.getDays() * workshiftsPerDay)
                    .reservedEquipment(equipment)
                    .totalSquare(square)
                    .workStatus(WorkStatus.NOT_STARTED)
                    .build()
            );
        }
        return allData;
    }

    private LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(date, formatter);
    }
}
