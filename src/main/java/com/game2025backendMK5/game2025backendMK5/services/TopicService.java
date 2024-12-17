package com.game2025backendMK5.game2025backendMK5.services;


import com.game2025backendMK5.game2025backendMK5.model.Topic;
import com.game2025backendMK5.game2025backendMK5.repo.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> getTopicsByQuest(String questType, String questSubtype) {
        return topicRepository.findByQuestTypeAndQuestSubtype(questType, questSubtype);
    }

    public List<Topic> getAllTopicsByQuestType(String questType) {
        // If you need all topics of a particular quest type (e.g., all Main or all Side)
        // You'd need a suitable repository method, e.g.:
        // return topicRepository.findByQuestType(questType);
        // For demonstration, assume such a method exists or implement it accordingly.
        return List.of();
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
}
