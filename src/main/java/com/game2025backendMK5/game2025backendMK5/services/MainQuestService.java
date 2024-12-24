package com.game2025backendMK5.game2025backendMK5.services;




import com.game2025backendMK5.game2025backendMK5.DTOs.QuestProgressDTO;
import com.game2025backendMK5.game2025backendMK5.model.Topic;
import com.game2025backendMK5.game2025backendMK5.model.UserProgress;
import com.game2025backendMK5.game2025backendMK5.repo.TopicRepository;
import com.game2025backendMK5.game2025backendMK5.repo.UserProgressRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MainQuestService {

    private final TopicRepository topicRepository;
    private final UserProgressRepository userProgressRepository;

    // Define your main quest subtypes and their corresponding routes
    private static final List<String> MAIN_QUEST_SUBTYPES = Arrays.asList("Java", "DSA", "Spring Boot", "SQL");
    private static final Map<String, String> QUEST_ROUTES = new HashMap<>();

    static {
        QUEST_ROUTES.put("Java", "/main-quest/java");
        QUEST_ROUTES.put("DSA", "/main-quest/dsa");
        QUEST_ROUTES.put("Spring Boot", "/main-quest/spring-boot");
        QUEST_ROUTES.put("SQL", "/main-quest/sql");
    }

    public MainQuestService(TopicRepository topicRepository, UserProgressRepository userProgressRepository) {
        this.topicRepository = topicRepository;
        this.userProgressRepository = userProgressRepository;
    }

    public List<QuestProgressDTO> getMainQuestsProgress(Long userId) {
        List<QuestProgressDTO> results = new ArrayList<>();

        for (String subtype : MAIN_QUEST_SUBTYPES) {
            List<Topic> topics = topicRepository.findByQuestTypeAndQuestSubtype("Main", subtype);
            int progress = calculateUserProgress(userId, topics);
            results.add(new QuestProgressDTO(subtype, progress, QUEST_ROUTES.get(subtype)));
        }

        return results;
    }

    private int calculateUserProgress(Long userId, List<Topic> topics) {
        if (topics.isEmpty()) {
            return 0;
        }

        // Fetch user progress for these topics
        List<Long> topicIds = topics.stream().map(Topic::getId).toList();
        // Filter userProgress by these topicIds and user
        List<UserProgress> userProgressList = userProgressRepository.findAll()
                .stream()
                .filter(up -> up.getUserId().equals(userId) && topicIds.contains(up.getTopic().getId()))
                .toList();

        long completedCount = userProgressList.stream().filter(UserProgress::isCompleted).count();
        return (int) ((completedCount * 100.0) / topics.size());
    }
}
