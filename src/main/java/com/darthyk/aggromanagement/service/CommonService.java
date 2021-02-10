package com.darthyk.aggromanagement.service;

import com.darthyk.aggromanagement.model.entity.Peas;
import com.darthyk.aggromanagement.model.entity.SpringWheat;
import com.darthyk.aggromanagement.model.entity.SugarBeet;
import com.darthyk.aggromanagement.model.entity.WinterWheat;
import com.darthyk.aggromanagement.model.entity.WorkType;
import com.darthyk.aggromanagement.repository.PeasRepository;
import com.darthyk.aggromanagement.repository.SpringWheatRepository;
import com.darthyk.aggromanagement.repository.SugarBeetRepository;
import com.darthyk.aggromanagement.repository.WinterWheatRepository;
import com.darthyk.aggromanagement.repository.WorkTypeRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
    @Autowired
    private PeasRepository peasRepository;
    @Autowired
    private SpringWheatRepository springWheatRepository;
    @Autowired
    private WinterWheatRepository winterWheatRepository;
    @Autowired
    private SugarBeetRepository sugarBeetRepository;
    @Autowired
    private WorkTypeRepository workTypeRepository;

    public void fillWorkTypes() {
        Set<String> worktypes = new HashSet<>();
        List<Peas> allWorktypesPeas = peasRepository.findAll();
        List<SugarBeet> allSugarBeetWorkTypes = sugarBeetRepository.findAll();
        List<SpringWheat> allSpringWheatWorkTypes = springWheatRepository.findAll();
        List<WinterWheat> allWinterWheatWorkTypes = winterWheatRepository.findAll();
        for (Peas peas : allWorktypesPeas) {
            worktypes.add(peas.getWorkType().trim());
        }
        for (SugarBeet sugarBeet : allSugarBeetWorkTypes) {
            worktypes.add(sugarBeet.getWorkType().trim());
        }
        for (SpringWheat springWheat : allSpringWheatWorkTypes) {
            worktypes.add(springWheat.getWorkType().trim());
        }
        for (WinterWheat winterWheat : allWinterWheatWorkTypes) {
            worktypes.add(winterWheat.getWorkType().trim());
        }
        workTypeRepository.saveAll(worktypes.stream().map(w -> WorkType.builder()
                .name(w)
                .build())
                .collect(Collectors.toList()));
    }
}
