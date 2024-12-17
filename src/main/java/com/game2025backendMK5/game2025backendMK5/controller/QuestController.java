package com.game2025backendMK5.game2025backendMK5.controller;




import com.game2025backendMK5.game2025backendMK5.DTOs.UserTopicDTO;
import com.game2025backendMK5.game2025backendMK5.model.UserProgress;
import com.game2025backendMK5.game2025backendMK5.services.CompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quests")
@CrossOrigin(origins = "*")
public class QuestController {



    private final CompletionService completionService;

    public QuestController(CompletionService completionService) {
        this.completionService = completionService;
    }

    /**
     * Endpoint to get user topics for a particular questType and questSubtype, along with completion info.
     * Example: GET /api/quests/user/1?questType=Main&questSubtype=Java
     */
    @GetMapping("/user/{userId}")
    public QuestDataResponse getUserTopicsForSubQuest(
            @PathVariable Long userId,
            @RequestParam String questType,
            @RequestParam String questSubtype) {

        List<UserTopicDTO> userTopics = completionService.getUserTopicsForSubQuest(userId, questType, questSubtype);
        double completionPercentage = completionService.computeCompletionPercentage(userTopics);

        // You might also want to get overall main quest completion, side quest completion, or total game completion:
        double mainCompletion = completionService.computeQuestTypeCompletion(userId, "Main");
        double sideCompletion = completionService.computeQuestTypeCompletion(userId, "Side");
        double overallCompletion = completionService.computeOverallCompletion(userId);

        return new QuestDataResponse(userTopics, completionPercentage, mainCompletion, sideCompletion, overallCompletion);
    }

    static class QuestDataResponse {
        private List<UserTopicDTO> topics;
        private double subQuestCompletion;
        private double mainQuestCompletion;
        private double sideQuestCompletion;
        private double overallCompletion;

        public QuestDataResponse(List<UserTopicDTO> topics, double subQuestCompletion, double mainQuestCompletion, double sideQuestCompletion, double overallCompletion) {
            this.topics = topics;
            this.subQuestCompletion = subQuestCompletion;
            this.mainQuestCompletion = mainQuestCompletion;
            this.sideQuestCompletion = sideQuestCompletion;
            this.overallCompletion = overallCompletion;
        }

        // Getters
        public List<UserTopicDTO> getTopics() { return topics; }
        public double getSubQuestCompletion() { return subQuestCompletion; }
        public double getMainQuestCompletion() { return mainQuestCompletion; }
        public double getSideQuestCompletion() { return sideQuestCompletion; }
        public double getOverallCompletion() { return overallCompletion; }
    }


    // 2. Update topic completion stat
}
