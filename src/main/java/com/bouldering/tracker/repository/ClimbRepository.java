package com.bouldering.tracker.repository;

import com.bouldering.tracker.model.Climb;
import com.bouldering.tracker.model.ClimbStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClimbRepository extends JpaRepository<Climb, Long> {

    // Find all climbs by status
    List<Climb> findByStatus(ClimbStatus status);

    // Find all climbs by location
    List<Climb> findByLocation(String location);

    // Find all climbs by grade
    List<Climb> findByGrade(String grade);

    // Find climbs by status ordered by date
    List<Climb> findByStatusOrderByDateSentDesc(ClimbStatus status);

    // Custom query: Get grade distribution for pyramid chart
    @Query("SELECT c.grade, COUNT(c) FROM Climb c WHERE c.status = 'SENT' GROUP BY c.grade ORDER BY c.grade")
    List<Object[]> getGradeDistribution();

    // Custom query: Get total sends by location
    @Query("SELECT c.location, COUNT(c) FROM Climb c WHERE c.status = 'SENT' GROUP BY c.location")
    List<Object[]> getSendsByLocation();

    // Find all climbs ordered by date
    List<Climb> findAllByOrderByCreatedAtDesc();
}