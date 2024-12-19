package com.game2025backendMK5.game2025backendMK5.services;


import com.game2025backendMK5.game2025backendMK5.DTOs.QuestProgressDTO;
import com.game2025backendMK5.game2025backendMK5.model.Topic;
import com.game2025backendMK5.game2025backendMK5.model.UserProgress;
import com.game2025backendMK5.game2025backendMK5.repo.TopicRepository;
import com.game2025backendMK5.game2025backendMK5.repo.UserProgressRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SideQuestService {

    private final TopicRepository topicRepository;
    private final UserProgressRepository userProgressRepository;

    // Define the side quest subtypes and their routes here:
    private static final List<String> SIDE_QUEST_SUBTYPES = Arrays.asList("AdditionalJava", "CloudBasics", "UIUX");
    private static final Map<String, String> QUEST_ROUTES = new HashMap<>();

    static {
        QUEST_ROUTES.put("AdditionalJava", "/side-quest/additional-java");
        QUEST_ROUTES.put("CloudBasics", "/side-quest/cloud-basics");
        QUEST_ROUTES.put("UIUX", "/side-quest/ui-ux");
    }

    public SideQuestService(TopicRepository topicRepository, UserProgressRepository userProgressRepository) {
        this.topicRepository = topicRepository;
        this.userProgressRepository = userProgressRepository;
    }

    public List<QuestProgressDTO> getSideQuestsProgress(Long userId) {
        List<QuestProgressDTO> results = new ArrayList<>();

        for (String subtype : SIDE_QUEST_SUBTYPES) {
            List<Topic> topics = topicRepository.findByQuestTypeAndQuestSubtype("Side", subtype);
            double progress = calculateUserProgress(userId, topics);
            results.add(new QuestProgressDTO(subtype, progress, QUEST_ROUTES.get(subtype)));
        }

        return results;
    }

    private double calculateUserProgress(Long userId, List<Topic> topics) {
        if (topics.isEmpty()) {
            return 0;
        }

        List<Long> topicIds = topics.stream().map(Topic::getId).toList();

        // For better performance, you could define a repository method:
        // List<UserProgress> findByUserIdAndTopic_IdIn(Long userId, List<Long> topicIds);
        // If not, you can filter from findAll:
        List<UserProgress> userProgressList = userProgressRepository.findAll().stream()
                .filter(up -> up.getUserId().equals(userId) && topicIds.contains(up.getTopic().getId()))
                .toList();

        long completedCount = userProgressList.stream().filter(UserProgress::isCompleted).count();
        return (completedCount * 100.0) / topics.size();
    }
}
