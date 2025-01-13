package com.game2025backendMK5.game2025backendMK5.services;

import com.game2025backendMK5.game2025backendMK5.model.DailyConquest;
import com.game2025backendMK5.game2025backendMK5.repo.DailyConquestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DailyConquestService {
    private final DailyConquestRepository dailyConquestRepository;

    @Autowired
    public DailyConquestService(DailyConquestRepository dailyConquestRepository) {
        this.dailyConquestRepository = dailyConquestRepository;
    }

    public DailyConquest createOrUpdateDailyConquest(Long userId, String questSubtype, LocalDate goalDate, int goalCount,String questType) {
        // see if there's already a record for this user, subquest, date
        DailyConquest dailyConquest = dailyConquestRepository.findByUserIdAndGoalDateAndQuestSubtype(userId, goalDate, questSubtype)
                .orElse(new DailyConquest());

        dailyConquest.setUserId(userId);
        dailyConquest.setQuestSubtype(questSubtype);
        dailyConquest.setGoalDate(goalDate);
        dailyConquest.setGoalCount(goalCount);
        dailyConquest.setQuestType(questType);

        return dailyConquestRepository.save(dailyConquest);
    }

    public DailyConquest findConquestByDateAndType(Long userId, String questType, String questSubtype, LocalDate date) {
        return dailyConquestRepository.findByUserIdAndGoalDateAndQuestTypeAndQuestSubtype(userId, date, questType, questSubtype)
                .orElseThrow(() -> new RuntimeException("No conquest found for the given parameters."));
    }

    public DailyConquest incrementCompletedCount(Long userId, String questSubtype, LocalDate goalDate) {
        DailyConquest dailyConquest = dailyConquestRepository
                .findByUserIdAndGoalDateAndQuestSubtype(userId, goalDate, questSubtype)
                .orElseThrow(() -> new RuntimeException("No daily conquest set for this user on this date."));

        dailyConquest.setCompletedCount(dailyConquest.getCompletedCount() + 1);
        // check if goal is reached
        if (dailyConquest.getCompletedCount() >= dailyConquest.getGoalCount()) {
            dailyConquest.setAchieved(true);
        }
        return dailyConquestRepository.save(dailyConquest);
    }

    // You can also add methods to get all daily conquests for a user, a date range, etc.
}
