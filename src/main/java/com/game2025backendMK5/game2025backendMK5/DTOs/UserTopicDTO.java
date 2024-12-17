package com.game2025backendMK5.game2025backendMK5.DTOs;

public class UserTopicDTO {
    private Long id;
    private String name;
    private String description;
    private String questType;
    private String questSubtype;
    private boolean completed;

    public UserTopicDTO(Long id, String name, String description, String questType, String questSubtype, boolean completed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.questType = questType;
        this.questSubtype = questSubtype;
        this.completed = completed;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getQuestType() { return questType; }
    public String getQuestSubtype() { return questSubtype; }
    public boolean isCompleted() { return completed; }
}
