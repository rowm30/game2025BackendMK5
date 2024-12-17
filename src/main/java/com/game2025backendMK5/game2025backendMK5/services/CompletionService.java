package com.game2025backendMK5.game2025backendMK5.services;


import com.game2025backendMK5.game2025backendMK5.DTOs.UserTopicDTO;
import com.game2025backendMK5.game2025backendMK5.model.Topic;
import com.game2025backendMK5.game2025backendMK5.model.UserProgress;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompletionService {

    private final TopicService topicService;
    private final UserProgressService userProgressService;

    public CompletionService(TopicService topicService, UserProgressService userProgressService) {
        this.topicService = topicService;
        this.userProgressService = userProgressService;
    }

    /**
     * Returns a list of topics along with a "completed" flag for the given user.
     */
    public List<UserTopicDTO> getUserTopicsForSubQuest(Long userId, String questType, String questSubtype) {
        List<Topic> topics = topicService.getTopicsByQuest(questType, questSubtype);
        List<UserProgress> userProgressList = userProgressService.getUserProgressBySubtype(userId, questSubtype);

        // Map topicId -> completed for quick lookup
        Map<Long, Boolean> progressMap = userProgressList.stream()
                .collect(Collectors.toMap(up -> up.getTopic().getId(), UserProgress::isCompleted));

        return topics.stream()
                .map(t -> new UserTopicDTO(
                        t.getId(),
                        t.getName(),
                        t.getDescription(),
                        t.getQuestType(),
                        t.getQuestSubtype(),
                        progressMap.getOrDefault(t.getId(), false)
                ))
                .toList();
    }

    /**
     * Compute completion percentage for a given set of user topics.
     */
    public double computeCompletionPercentage(List<UserTopicDTO> userTopics) {
        long completedCount = userTopics.stream().filter(UserTopicDTO::isCompleted).count();
        return userTopics.isEmpty() ? 0 : (completedCount * 100.0 / userTopics.size());
    }

    /**
     * Compute overall completion across all Main or Side quests, or even overall game completion.
     * This might involve fetching all topics of that type and comparing with user's completed topics.
     */
    public double computeQuestTypeCompletion(Long userId, String questType) {
        // Get all topics for the given quest type
        List<Topic> allQuestTypeTopics = topicService.getAllTopicsByQuestType(questType);
        List<UserProgress> userProgress = userProgressService.getAllUserProgress(userId);

        // Map topicId to completed
        Map<Long, Boolean> userCompletedMap = userProgress.stream()
                .collect(Collectors.toMap(up -> up.getTopic().getId(), UserProgress::isCompleted));

        long completedCount = allQuestTypeTopics.stream()
                .filter(t -> userCompletedMap.getOrDefault(t.getId(), false))
                .count();

        return allQuestTypeTopics.isEmpty() ? 0 : (completedCount * 100.0 / allQuestTypeTopics.size());
    }

    /**
     * Compute overall game completion (Main + Side + any other quest type)
     */
    public double computeOverallCompletion(Long userId) {
        List<Topic> allTopics = topicService.getAllTopics();
        List<UserProgress> userProgress = userProgressService.getAllUserProgress(userId);

        Map<Long, Boolean> userCompletedMap = userProgress.stream()
                .collect(Collectors.toMap(up -> up.getTopic().getId(), UserProgress::isCompleted));

        long completedCount = allTopics.stream()
                .filter(t -> userCompletedMap.getOrDefault(t.getId(), false))
                .count();

        return allTopics.isEmpty() ? 0 : (completedCount * 100.0 / allTopics.size());
    }
}
