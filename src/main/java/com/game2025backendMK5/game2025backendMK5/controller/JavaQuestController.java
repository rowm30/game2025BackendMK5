package com.game2025backendMK5.game2025backendMK5.controller;



import com.game2025backendMK5.game2025backendMK5.model.Topic;
import com.game2025backendMK5.game2025backendMK5.model.UserProgress;
import com.game2025backendMK5.game2025backendMK5.repo.TopicRepository;
import com.game2025backendMK5.game2025backendMK5.repo.UserProgressRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class JavaQuestController {

    private final TopicRepository topicRepository;
    private final UserProgressRepository userProgressRepository;

    public JavaQuestController(TopicRepository topicRepository,
                               UserProgressRepository userProgressRepository) {
        this.topicRepository = topicRepository;
        this.userProgressRepository = userProgressRepository;
    }

    /**
     * Endpoint to get Java Quest topics for a specific user.
     * Example call: GET /api/java-quest?userId=1&questType=Main&questSubtype=Java
     *
     * If you don't want to pass questType/subtype from frontend,
     * you can hard-code 'Main' and 'Java'.
     */
    @GetMapping("/java-quest")
    public List<UserTopicDTO> getJavaQuestTopics(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "Main") String questType,
            @RequestParam(defaultValue = "Java") String questSubtype) {

        // Fetch all topics for this questType & questSubtype
        List<Topic> topics = topicRepository.findByQuestTypeAndQuestSubtype(questType, questSubtype);

        // Fetch user progress for these topics
        List<UserProgress> userProgressList = userProgressRepository.findByUserIdAndTopic_QuestSubtype(userId, questSubtype);

        // Create a map of topicId to completion status for quick lookup
        Map<Long, Boolean> completionMap = userProgressList.stream()
                .collect(Collectors.toMap(up -> up.getTopic().getId(), UserProgress::isCompleted));

        // Map the topics to DTO including completion status
        return topics.stream()
                .map(topic -> new UserTopicDTO(
                        topic.getId(),
                        topic.getName(),
                        topic.getDescription(),
                        completionMap.getOrDefault(topic.getId(), false)
                ))
                .collect(Collectors.toList());
    }

    // A simple DTO class to return to the frontend
    public static class UserTopicDTO {
        private Long id;
        private String name;
        private String description;
        private boolean completed;

        public UserTopicDTO(Long id, String name, String description, boolean completed) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.completed = completed;
        }

        // Getters
        public Long getId() { return id; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public boolean isCompleted() { return completed; }
    }
}
