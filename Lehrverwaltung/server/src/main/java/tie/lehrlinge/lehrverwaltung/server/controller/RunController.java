package tie.lehrlinge.lehrverwaltung.server.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tie.lehrlinge.lehrverwaltung.server.model.NotificationModel;
import tie.lehrlinge.lehrverwaltung.server.model.RunModel;
import tie.lehrlinge.lehrverwaltung.server.model.dto.CreateRunDTO;
import tie.lehrlinge.lehrverwaltung.server.model.entity.Run;
import tie.lehrlinge.lehrverwaltung.server.service.RunService;

import java.util.List;

@RestController
@RequestMapping("/run")
@Log4j2
public class RunController {

    private static boolean isASolutionRunning;

    private final RunService runService;

    public RunController(RunService runService) {
        this.runService = runService;
        isASolutionRunning = false;
    }

    @PreAuthorize("hasAnyAuthority('Lehrling', 'Lehrmeister')")
    @GetMapping("/status")
    public boolean isIsASolutionRunning() {
        return isASolutionRunning;
    }

    @PreAuthorize("hasAnyAuthority('Lehrling', 'Lehrmeister')")
    @PostMapping
    public Boolean runSolution(@RequestBody CreateRunDTO createRunDTO) throws InterruptedException {
        isASolutionRunning = true;
        isASolutionRunning = runService.runSolution(createRunDTO);
        return true;
    }


    @PreAuthorize("hasAnyAuthority('Lehrling', 'Lehrmeister')")
    @GetMapping("/{exerciseId}/notification/{username}")
    public NotificationModel getLastNotification(@PathVariable int exerciseId, @PathVariable String username) {
        return runService.getLatestNotificationForExercise(exerciseId, username);
    }

    @PreAuthorize("hasAnyAuthority('Lehrling', 'Lehrmeister')")
    @GetMapping("/{exerciseId}/leaderboard/{username}")
    public List<RunModel> getBestRunsForExercise(@PathVariable int exerciseId, @PathVariable String username) {
        return this.runService.getRankingListView(exerciseId, username);
    }
}
