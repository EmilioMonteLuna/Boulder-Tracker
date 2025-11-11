package com.bouldering.tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "climbs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Climb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Climb name is required")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Grade is required")
    @Column(nullable = false)
    private String grade; // V0, V1, V2, etc.

    @NotBlank(message = "Location is required")
    @Column(nullable = false)
    private String location; // gym name or outdoor crag

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClimbStatus status; // SENT, PROJECT, FLASHED

    @Column(columnDefinition = "TEXT")
    private String notes; // beta, technique notes

    private String photoUrl; // path to uploaded photo

    @Column(nullable = false)
    private Integer attempts; // number of attempts before send

    @Column(name = "date_sent")
    private LocalDateTime dateSent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (attempts == null) {
            attempts = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

