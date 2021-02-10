package com.darthyk.aggromanagement.model.dto;

import java.util.List;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Report {
    private List<Culture> culture;
}
