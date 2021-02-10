package com.darthyk.aggromanagement.model.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Culture {
    private String name;
    private List<String> fields;
    private String workType;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> trailers;
}
