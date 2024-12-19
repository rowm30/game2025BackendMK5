package com.game2025backendMK5.game2025backendMK5.DTOs;



public class QuestProgressDTO {
    private String name;
    private double progress;
    private String route;

    public QuestProgressDTO(String name, double progress, String route) {
        this.name = name;
        this.progress = progress;
        this.route = route;
    }

    public String getName() { return name; }
    public double getProgress() { return progress; }
    public String getRoute() { return route; }
}
