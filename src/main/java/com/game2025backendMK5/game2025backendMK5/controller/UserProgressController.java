package com.game2025backendMK5.game2025backendMK5.controller;


import com.game2025backendMK5.game2025backendMK5.services.UserProgressService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-progress")
@CrossOrigin(origins = "*")
public class UserProgressController {

    private final UserProgressService userProgressService;

    public UserProgressController(UserProgressService userProgressService) {
        this.userProgressService = userProgressService;
    }

    @PostMapping("/update")
    public void updateUserProgress(@RequestBody UpdateProgressRequest request) {
        userProgressService.updateUserProgress(request.getUserId(), request.getTopicId(), request.isCompleted());
    }

    // DTO for the request body
    static class UpdateProgressRequest {
        private Long userId;
        private Long topicId;
        private boolean completed;

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }

        public Long getTopicId() { return topicId; }
        public void setTopicId(Long topicId) { this.topicId = topicId; }

        public boolean isCompleted() { return completed; }
        public void setCompleted(boolean completed) { this.completed = completed; }
    }
}
