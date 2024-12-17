package com.game2025backendMK5.game2025backendMK5.services;


import com.game2025backendMK5.game2025backendMK5.model.Topic;
import com.game2025backendMK5.game2025backendMK5.model.User;
import com.game2025backendMK5.game2025backendMK5.model.UserProgress;
import com.game2025backendMK5.game2025backendMK5.repo.TopicRepository;
import com.game2025backendMK5.game2025backendMK5.repo.UserProgressRepository;
import com.game2025backendMK5.game2025backendMK5.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProgressService {
    private final UserProgressRepository userProgressRepository;
    private final UserRepo userRepo;
    private final TopicRepository topicRepository;

    public UserProgressService(UserProgressRepository userProgressRepository,
                               UserRepo userRepo,
                               TopicRepository topicRepository) {
        this.userProgressRepository = userProgressRepository;
        this.userRepo = userRepo;
        this.topicRepository = topicRepository;
    }

    public List<UserProgress> getUserProgressBySubtype(Long userId, String questSubtype) {
        return userProgressRepository.findByUserIdAndTopic_QuestSubtype(userId, questSubtype);
    }

    // Method to get all user progress (might be needed for overall completion calculations)
    public List<UserProgress> getAllUserProgress(Long userId) {
        // If you need a method to fetch all user progress:
        // return userProgressRepository.findByUserId(userId);
        // Make sure such a repository method exists, if needed.
        return userProgressRepository.findAll().stream()
                .filter(up -> up.getUserId().equals(userId))
                .toList();
    }






    public void updateUserProgress(Long userId, Long topicId, boolean completed) {
        // Find the user
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Find the topic
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found with id: " + topicId));

        // Find existing UserProgress or create a new one
        Optional<UserProgress> existingUP = userProgressRepository.findByUserIdAndTopic_Id(userId, topicId);
        UserProgress userProgress = existingUP.orElseGet(() -> {
            UserProgress up = new UserProgress();
            up.setUserId(user.getId());
            up.setTopic(topic);
            return up;
        });

        // Update completion status
        userProgress.setCompleted(completed);
        userProgressRepository.save(userProgress);
    }
}
