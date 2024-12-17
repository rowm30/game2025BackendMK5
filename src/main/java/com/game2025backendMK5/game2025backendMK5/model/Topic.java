package com.game2025backendMK5.game2025backendMK5.model;



import jakarta.persistence.*;

@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "quest_type", nullable = false)
    private String questType;

    @Column(name = "quest_subtype", nullable = false)
    private String questSubtype;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getQuestType() { return questType; }
    public void setQuestType(String questType) { this.questType = questType; }
    public String getQuestSubtype() { return questSubtype; }
    public void setQuestSubtype(String questSubtype) { this.questSubtype = questSubtype; }
}
