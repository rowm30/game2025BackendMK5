package com.game2025backendMK5.game2025backendMK5.controller;


import com.game2025backendMK5.game2025backendMK5.DTOs.QuestProgressDTO;
import com.game2025backendMK5.game2025backendMK5.services.MainQuestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MainQuestController {

    private final MainQuestService mainQuestService;

    public MainQuestController(MainQuestService mainQuestService) {
        this.mainQuestService = mainQuestService;
    }

    @GetMapping("/main-quests")
    public List<QuestProgressDTO> getMainQuests(@RequestParam Long userId) {
        return mainQuestService.getMainQuestsProgress(userId);
    }
}
