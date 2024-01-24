package tie.lehrlinge.lehrverwaltung.server.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tie.lehrlinge.lehrverwaltung.server.model.ExerciseModel;
import tie.lehrlinge.lehrverwaltung.server.model.dto.ExerciseDTO;
import tie.lehrlinge.lehrverwaltung.server.service.ExerciseService;

@RestController
@RequestMapping("/exercise-management/exercises")
public class ExerciseController {

  private final ExerciseService exerciseService;

  public ExerciseController(ExerciseService exerciseService) {
    this.exerciseService = exerciseService;
  }

  @GetMapping
  @PreAuthorize("hasAnyAuthority('Lehrling', 'Lehrmeister')")
  public List<ExerciseModel> getExercises() {
    return exerciseService.getExercises();
  }

  @PostMapping
  @PreAuthorize("hasAnyAuthority('Lehrmeister')")
  public void createExercise(@RequestBody ExerciseDTO exerciseDTO) {
    exerciseService.createExercise(exerciseDTO);
  }
}

