package com.game2025backendMK5.game2025backendMK5.repo;

import com.game2025backendMK5.game2025backendMK5.model.DailyConquest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyConquestRepository extends JpaRepository<DailyConquest, Long> {
    // e.g. find all daily conquests for a user on a specific date
    Optional<DailyConquest> findByUserIdAndGoalDateAndQuestSubtype(Long userId, LocalDate goalDate, String questSubtype);

    // find all daily conquests for a user in a date range
    List<DailyConquest> findByUserIdAndGoalDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    Optional<DailyConquest> findByUserIdAndGoalDateAndQuestTypeAndQuestSubtype(Long userId, LocalDate goalDate, String questType, String questSubtype);

    // etc. as needed
}
