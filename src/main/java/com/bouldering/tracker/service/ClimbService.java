package com.bouldering.tracker.service;

import com.bouldering.tracker.dto.ClimbDTO;
import com.bouldering.tracker.dto.GradeStatsDTO;
import com.bouldering.tracker.exception.ResourceNotFoundException;
import com.bouldering.tracker.model.Climb;
import com.bouldering.tracker.model.ClimbStatus;
import com.bouldering.tracker.repository.ClimbRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClimbService {

    private final ClimbRepository climbRepository;

    public List<Climb> getAllClimbs() {
        return climbRepository.findAllByOrderByCreatedAtDesc();
    }

    public Climb getClimbById(Long id) {
        return climbRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Climb not found with id: " + id));
    }

    @Transactional
    public Climb createClimb(ClimbDTO climbDTO) {
        Climb climb = new Climb();
        climb.setName(climbDTO.getName());
        climb.setGrade(climbDTO.getGrade());
        climb.setLocation(climbDTO.getLocation());
        climb.setStatus(ClimbStatus.valueOf(climbDTO.getStatus()));
        climb.setNotes(climbDTO.getNotes());
        climb.setAttempts(climbDTO.getAttempts() != null ? climbDTO.getAttempts() : 0);

        if (climb.getStatus() == ClimbStatus.SENT || climb.getStatus() == ClimbStatus.FLASHED) {
            climb.setDateSent(LocalDateTime.now());
        }

        return climbRepository.save(climb);
    }

    @Transactional
    public Climb updateClimb(Long id, ClimbDTO climbDTO) {
        Climb climb = getClimbById(id);

        climb.setName(climbDTO.getName());
        climb.setGrade(climbDTO.getGrade());
        climb.setLocation(climbDTO.getLocation());

        ClimbStatus newStatus = ClimbStatus.valueOf(climbDTO.getStatus());
        ClimbStatus oldStatus = climb.getStatus();

        climb.setStatus(newStatus);
        climb.setNotes(climbDTO.getNotes());
        climb.setAttempts(climbDTO.getAttempts());

        // Set dateSent when status changes to SENT or FLASHED
        if ((newStatus == ClimbStatus.SENT || newStatus == ClimbStatus.FLASHED) &&
                oldStatus == ClimbStatus.PROJECT) {
            climb.setDateSent(LocalDateTime.now());
        }

        return climbRepository.save(climb);
    }

    @Transactional
    public void deleteClimb(Long id) {
        Climb climb = getClimbById(id);
        climbRepository.delete(climb);
    }

    public List<Climb> getClimbsByStatus(String status) {
        ClimbStatus climbStatus = ClimbStatus.valueOf(status.toUpperCase());
        return climbRepository.findByStatusOrderByDateSentDesc(climbStatus);
    }

    public List<Climb> getClimbsByLocation(String location) {
        return climbRepository.findByLocation(location);
    }

    // Get pyramid data for visualization
    public List<GradeStatsDTO> getPyramidStats() {
        List<Object[]> results = climbRepository.getGradeDistribution();
        return results.stream()
                .map(row -> new GradeStatsDTO((String) row[0], ((Long) row[1]).intValue()))
                .collect(Collectors.toList());
    }

    // Get statistics
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();

        List<Climb> allClimbs = climbRepository.findAll();
        long totalSends = allClimbs.stream().filter(c -> c.getStatus() == ClimbStatus.SENT || c.getStatus() == ClimbStatus.FLASHED).count();
        long projects = allClimbs.stream().filter(c -> c.getStatus() == ClimbStatus.PROJECT).count();

        stats.put("totalClimbs", allClimbs.size());
        stats.put("totalSends", totalSends);
        stats.put("activeProjects", projects);
        stats.put("pyramid", getPyramidStats());

        return stats;
    }
}