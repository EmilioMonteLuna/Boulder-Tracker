package com.bouldering.tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for grade statistics
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeStatsDTO {
    private String grade;
    private Integer count;
}