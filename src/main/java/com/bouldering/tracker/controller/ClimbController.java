package com.bouldering.tracker.controller;

import com.bouldering.tracker.dto.ClimbDTO;
import com.bouldering.tracker.model.Climb;
import com.bouldering.tracker.service.ClimbService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/climbs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ClimbController {

    private final ClimbService climbService;

    @GetMapping
    public ResponseEntity<List<Climb>> getAllClimbs() {
        return ResponseEntity.ok(climbService.getAllClimbs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Climb> getClimbById(@PathVariable Long id) {
        return ResponseEntity.ok(climbService.getClimbById(id));
    }

    @PostMapping
    public ResponseEntity<Climb> createClimb(@Valid @RequestBody ClimbDTO climbDTO) {
        Climb createdClimb = climbService.createClimb(climbDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClimb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Climb> updateClimb(
            @PathVariable Long id,
            @Valid @RequestBody ClimbDTO climbDTO) {
        Climb updatedClimb = climbService.updateClimb(id, climbDTO);
        return ResponseEntity.ok(updatedClimb);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClimb(@PathVariable Long id) {
        climbService.deleteClimb(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Climb>> getClimbsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(climbService.getClimbsByStatus(status));
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Climb>> getClimbsByLocation(@PathVariable String location) {
        return ResponseEntity.ok(climbService.getClimbsByLocation(location));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        return ResponseEntity.ok(climbService.getStatistics());
    }
}
