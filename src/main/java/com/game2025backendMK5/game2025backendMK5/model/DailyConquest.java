package com.game2025backendMK5.game2025backendMK5.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "daily_conquest")
public class DailyConquest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "quest_type") // optional if you want daily goals per type
    private String questType;

    @Column(name = "quest_subtype") // e.g., "Java"
    private String questSubtype;

    @Column(name = "goal_date", nullable = false)
    private LocalDate goalDate;

    @Column(name = "goal_count", nullable = false)
    private int goalCount;

    @Column(name = "completed_count", nullable = false)
    private int completedCount;

    @Column(name = "achieved", nullable = false)
    private boolean achieved;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getQuestType() {
        return questType;
    }

    public void setQuestType(String questType) {
        this.questType = questType;
    }

    public String getQuestSubtype() {
        return questSubtype;
    }

    public void setQuestSubtype(String questSubtype) {
        this.questSubtype = questSubtype;
    }

    public LocalDate getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(LocalDate goalDate) {
        this.goalDate = goalDate;
    }

    public int getGoalCount() {
        return goalCount;
    }

    public void setGoalCount(int goalCount) {
        this.goalCount = goalCount;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }
}

