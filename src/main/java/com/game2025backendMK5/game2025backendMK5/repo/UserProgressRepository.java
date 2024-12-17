package com.game2025backendMK5.game2025backendMK5.repo;


import com.game2025backendMK5.game2025backendMK5.model.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    List<UserProgress> findByUserIdAndTopic_QuestSubtype(Long userId, String questSubtype);

    Optional<UserProgress> findByUserIdAndTopic_Id(Long userId, Long topicId);
}
