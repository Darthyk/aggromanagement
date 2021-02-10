package com.darthyk.aggromanagement.model;

import java.util.List;
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
public class Equipment {
    private String name;
    private List<String> workTypes;
    List<WorkCapabilities> capabilities;
}
