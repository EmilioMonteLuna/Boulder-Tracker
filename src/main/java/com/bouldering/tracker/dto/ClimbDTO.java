package com.bouldering.tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClimbDTO {

    @NotBlank(message = "Climb name is required")
    private String name;

    @NotNull(message = "Grade is required")
    private String grade;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Status is required")
    private String status; // PROJECT, SENT, FLASHED

    private String notes;

    private Integer attempts;

    private String photoUrl;
}

