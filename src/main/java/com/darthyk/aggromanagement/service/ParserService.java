package com.darthyk.aggromanagement.service;

import com.darthyk.aggromanagement.csvparser.CsvTableParser;
import com.darthyk.aggromanagement.model.entity.Culture;
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
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParserService {
    @Autowired
    private PeasRepository peasRepository;

    @Autowired
    private SpringWheatRepository springWheatRepository;

    @Autowired
    private WinterWheatRepository winterWheatRepository;

    @Autowired
    private SugarBeetRepository sugarBeetRepository;

    @Autowired
    private ProductionRateRepository productionRateRepository;

    @Autowired
    private StructureSovingRepository structureSovingRepository;

    @Autowired
    private TrailerRepository trailerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private WorkshiftNumberRepository workshiftNumberRepository;

    public void startPeasParse() throws IOException, URISyntaxException {
        File file;
        file = new File(getClass().getResource("/culture/peas_worktype.csv").toURI());
        CSVParser csvParser = CsvTableParser.parseFile(file);
        List<Peas> peasList = new ArrayList<>();
        for (CSVRecord record : csvParser.getRecords()) {
            Peas peas = Peas.builder()
                    .workType(record.get(0))
                    .startDate(record.get(1))
                    .endDate(record.get(2))
                    .build();
            peasList.add(peas);
        }
        peasRepository.saveAll(peasList);
        peasRepository.flush();
    }

    public void startSpringWheatParse() throws IOException, URISyntaxException {
        File file;
        file = new File(getClass().getResource("/culture/spring_wheat_worktype.csv").toURI());
        CSVParser csvParser = CsvTableParser.parseFile(file);
        List<SpringWheat> springWheats = new ArrayList<SpringWheat>();
        for (CSVRecord record : csvParser.getRecords()) {
            SpringWheat springWheat = SpringWheat.builder()
                    .workType(record.get(0))
                    .startDate(record.get(1))
                    .endDate(record.get(2))
                    .build();
            springWheats.add(springWheat);
        }
        springWheatRepository.saveAll(springWheats);
        springWheatRepository.flush();
    }

    public void startWinterWheatParse() throws IOException, URISyntaxException {
        File file;
        file = new File(getClass().getResource("/culture/winter_wheat_worktype.csv").toURI());
        CSVParser csvParser = CsvTableParser.parseFile(file);
        List<WinterWheat> winterWheats = new ArrayList<WinterWheat>();
        for (CSVRecord record : csvParser.getRecords()) {
            WinterWheat winterWheat = WinterWheat.builder()
                    .workType(record.get(0))
                    .startDate(record.get(1))
                    .endDate(record.get(2))
                    .build();
            winterWheats.add(winterWheat);
        }
        winterWheatRepository.saveAll(winterWheats);
        winterWheatRepository.flush();
    }

    public void startSugarBeetParse() throws IOException, URISyntaxException {
        File file;
        file = new File(getClass().getResource("/culture/sugar_beet_worktype.csv").toURI());
        CSVParser csvParser = CsvTableParser.parseFile(file);
        List<SugarBeet> sugarBeets = new ArrayList<SugarBeet>();
        for (CSVRecord record : csvParser.getRecords()) {
            SugarBeet sugarBeet = SugarBeet.builder()
                    .workType(record.get(0))
                    .startDate(record.get(1))
                    .endDate(record.get(2))
                    .build();
            sugarBeets.add(sugarBeet);
        }
        sugarBeetRepository.saveAll(sugarBeets);
        sugarBeetRepository.flush();
    }

    public void startProductionRateParse() throws IOException, URISyntaxException {
        File file;
        file = new File(getClass().getResource("/production_rate.csv").toURI());
        CSVParser csvParser = CsvTableParser.parseFile(file);
        List<ProductionRate> productionRates = new ArrayList<>();
        for (CSVRecord record : csvParser.getRecords()) {
            ProductionRate productionRate = ProductionRate.builder()
                    .vehicleType(record.get(0))
                    .trailerType(record.get(1))
                    .workType(record.get(2))
                    .productionRateForDay(Double.valueOf(record.get(3).replace(',', '.')))
                    .build();
            productionRates.add(productionRate);
        }
        productionRateRepository.saveAll(productionRates);
        productionRateRepository.flush();
    }

    public void startSowingStructureParse() throws IOException, URISyntaxException {
        File file;
        file = new File(getClass().getResource("/sowing_structure.csv").toURI());
        CSVParser csvParser = CsvTableParser.parseFile(file);
        List<StructureSowing> structureSowings = new ArrayList<>();
        for (CSVRecord record : csvParser.getRecords()) {
            StructureSowing structureSowing = StructureSowing.builder()
                    .fieldNumber(record.get(0))
                    .square(Integer.valueOf(record.get(1)))
                    .culture(record.get(2))
                    .build();
            structureSowings.add(structureSowing);
        }
        structureSovingRepository.saveAll(structureSowings);
        structureSovingRepository.flush();
    }

    public void startWorkshiftNumberParse() throws IOException, URISyntaxException {
        File file;
        file = new File(getClass().getResource("/workshift_number.csv").toURI());
        CSVParser csvParser = CsvTableParser.parseFile(file);
        List<WorkshiftNumber> workshiftNumbers = new ArrayList<>();
        for (CSVRecord record : csvParser.getRecords()) {
            WorkshiftNumber workshiftNumber = WorkshiftNumber.builder()
                    .workType(record.get(0))
                    .workshiftNumber(Integer.valueOf(record.get(1)))
                    .build();
            workshiftNumbers.add(workshiftNumber);
        }
        workshiftNumberRepository.saveAll(workshiftNumbers);
        workshiftNumberRepository.flush();
    }

    public void startVehicleParse() throws IOException, URISyntaxException {
        File file;
        file = new File(getClass().getResource("/vehicle.csv").toURI());
        CSVParser csvParser = CsvTableParser.parseFile(file);
        List<Vehicle> vehicles = new ArrayList<>();
        for (CSVRecord record : csvParser.getRecords()) {
            Vehicle vehicle = Vehicle.builder()
                    .vehicleName(record.get(0))
                    .vehicleType(record.get(1))
                    .build();
            vehicles.add(vehicle);
        }
        vehicleRepository.saveAll(vehicles);
        vehicleRepository.flush();
    }

    public void startTrailerParse() throws IOException, URISyntaxException {
        File file;
        file = new File(getClass().getResource("/trailer.csv").toURI());
        CSVParser csvParser = CsvTableParser.parseFile(file);
        List<Trailer> trailers = new ArrayList<>();
        for (CSVRecord record : csvParser.getRecords()) {
            Trailer trailer = Trailer.builder()
                    .trailerName(record.get(0))
                    .trailerType(record.get(1))
                    .build();
            trailers.add(trailer);
        }
        trailerRepository.saveAll(trailers);
        trailerRepository.flush();
    }
}
