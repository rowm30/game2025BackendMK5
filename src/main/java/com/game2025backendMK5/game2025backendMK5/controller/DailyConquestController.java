package com.game2025backendMK5.game2025backendMK5.controller;

import com.game2025backendMK5.game2025backendMK5.model.DailyConquest;
import com.game2025backendMK5.game2025backendMK5.services.DailyConquestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/daily-conquest")
@CrossOrigin(origins = "*")
public class DailyConquestController {

    private final DailyConquestService dailyConquestService;

    @Autowired
    public DailyConquestController(DailyConquestService dailyConquestService) {
        this.dailyConquestService = dailyConquestService;
    }

    /**
     * POST /api/daily-conquest/create
     * Body: { "userId": 7, "questSubtype": "Java", "goalCount": 3 }
     */
    @PostMapping("/create")
    public ResponseEntity< DailyConquest> createDailyConquest(@RequestBody CreateDailyConquestRequest request) {
        DailyConquest conquest = dailyConquestService.createOrUpdateDailyConquest(
                request.getUserId(),
                request.getQuestSubtype(),
                LocalDate.now(), // or any date the user picks
                request.getGoalCount(),
                request.getQuestType()

        );
        return ResponseEntity.ok(conquest);
    }

    @GetMapping("/today")
    public ResponseEntity<DailyConquest> getTodayConquest(
            @RequestParam Long userId,
            @RequestParam String questType,
            @RequestParam String questSubtype) {
        LocalDate today = LocalDate.now();
        DailyConquest dailyConquest = dailyConquestService.findConquestByDateAndType(userId, questType, questSubtype, today);
        return ResponseEntity.ok(dailyConquest);
    }

    /**
     * PATCH /api/daily-conquest/increment
     * Body: { "userId": 7, "questSubtype": "Java" }
     * This increments the user’s progress for that day by 1.
     */
    @PatchMapping("/increment")
    public ResponseEntity<DailyConquest> incrementDailyConquest(@RequestBody IncrementConquestRequest request) {
        DailyConquest updated = dailyConquestService.incrementCompletedCount(
                request.getUserId(),
                request.getQuestSubtype(),
                LocalDate.now()
        );
        return ResponseEntity.ok(updated);
    }

    // etc.

    // Nested static classes for request bodies or use DTOs
    static class CreateDailyConquestRequest {
        private Long userId;
        private String questSubtype;
        private String questType;
        private int goalCount;

        public Long getUserId() {
            return userId;
        }

        public String getQuestType() {
            return questType;
        }

        public void setQuestType(String questType) {
            this.questType = questType;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getQuestSubtype() {
            return questSubtype;
        }

        public void setQuestSubtype(String questSubtype) {
            this.questSubtype = questSubtype;
        }

        public int getGoalCount() {
            return goalCount;
        }

        public void setGoalCount(int goalCount) {
            this.goalCount = goalCount;
        }
    }

    static class IncrementConquestRequest {
        private Long userId;
        private String questSubtype;
        // getters/setters

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getQuestSubtype() {
            return questSubtype;
        }

        public void setQuestSubtype(String questSubtype) {
            this.questSubtype = questSubtype;
        }
    }
}

