package com.game2025backendMK5.game2025backendMK5.repo;



import com.game2025backendMK5.game2025backendMK5.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByQuestTypeAndQuestSubtype(String questType, String questSubtype);
}