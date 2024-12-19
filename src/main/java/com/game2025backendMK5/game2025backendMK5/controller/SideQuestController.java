package com.game2025backendMK5.game2025backendMK5.controller;


import com.game2025backendMK5.game2025backendMK5.DTOs.QuestProgressDTO;
import com.game2025backendMK5.game2025backendMK5.services.SideQuestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SideQuestController {

    private final SideQuestService sideQuestService;

    public SideQuestController(SideQuestService sideQuestService) {
        this.sideQuestService = sideQuestService;
    }

    @GetMapping("/side-quests")
    public List<QuestProgressDTO> getSideQuests(@RequestParam Long userId) {
        return sideQuestService.getSideQuestsProgress(userId);
    }
}
