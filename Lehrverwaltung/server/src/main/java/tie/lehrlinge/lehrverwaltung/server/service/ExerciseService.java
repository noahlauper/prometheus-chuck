package tie.lehrlinge.lehrverwaltung.server.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import tie.lehrlinge.lehrverwaltung.server.model.ExerciseModel;
import tie.lehrlinge.lehrverwaltung.server.model.dto.ExerciseDTO;
import tie.lehrlinge.lehrverwaltung.server.model.entity.Exercise;
import tie.lehrlinge.lehrverwaltung.server.model.entity.ExerciseDescription;
import tie.lehrlinge.lehrverwaltung.server.model.entity.ExerciseLink;
import tie.lehrlinge.lehrverwaltung.server.model.entity.FileObject;
import tie.lehrlinge.lehrverwaltung.server.repository.ExerciseDescriptionRepository;
import tie.lehrlinge.lehrverwaltung.server.repository.ExerciseLinkRepository;
import tie.lehrlinge.lehrverwaltung.server.repository.ExerciseRepository;
import tie.lehrlinge.lehrverwaltung.server.repository.FileObjectRepository;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseLinkRepository exerciseLinkRepository;
    private final ExerciseDescriptionRepository exerciseDescriptionRepository;

    private final FileObjectRepository fileObjectRepository;

    public ExerciseService(ExerciseRepository exerciseRepository,
                           ExerciseLinkRepository exerciseLinkRepository,
                           ExerciseDescriptionRepository exerciseDescriptionRepository,
                           FileObjectRepository fileObjectRepository) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseLinkRepository = exerciseLinkRepository;
        this.exerciseDescriptionRepository = exerciseDescriptionRepository;
        this.fileObjectRepository = fileObjectRepository;
    }

    public List<ExerciseModel> getExercises() {
        List<Exercise> exercises = exerciseRepository.findAll();
        List<ExerciseModel> exerciseModels = new ArrayList<>();
        log.debug("Gefunden: {} Einträge für die Aufgaben in der Datenbank", exercises.size());
        exercises.forEach(exercise -> {
            List<String> exerciseLinks = new ArrayList<>();
            ExerciseModel newExercise = new ExerciseModel(exercise.getId(), exercise.getTitle(), exercise.getTopic(), exercise.getRequirement(), exerciseLinks);
            if (exercise.getExerciseDescription() != null) {
                newExercise.setDescription(exercise.getExerciseDescription().getDescription());
                log.debug("Aufgabe mit dem Id: {} hat eine Beschreibung", exercise.getId());
            } else {
                log.debug("Aufgabe mit dem Id: {} hat keine Beschreibung", exercise.getId());
            }
            if (exercise.getPublicIO().size() != 0) {
                String content = "Input:\n" + exercise.getPublicIO().get(0) + "\nOutput:\n" + exercise.getPublicIO().get(1);
                log.debug("Textdatei mit den Inhalt:\n" + content + "\nwurde hinzugefügt");
                newExercise.setTextFile(content.getBytes(StandardCharsets.UTF_8));
                if (exercise.getCodeFile() != null) {
                    newExercise.setData(exercise.getCodeFile().getData());
                    newExercise.setName(exercise.getCodeFile().getName());
                    newExercise.setType(exercise.getCodeFile().getType());
                    log.debug("Aufgabe mit dem Id: {} hat eine Code Datei", exercise.getId());
                } else {
                    log.debug("Aufgabe mit dem Id: {} hat keine Code Datei", exercise.getId());
                }
            } else {
                log.debug("Aufgabe ist keine SMART-Aufgabe und hat keine Textdatei");
                newExercise.setTextFile(null);
            }
            exercise.getExerciseLinks().forEach(exerciseLink -> exerciseLinks.add(exerciseLink.getLink()));
            exerciseModels.add(newExercise);
        });
        return exerciseModels;
    }

    public void createExercise(ExerciseDTO exerciseDTO) {
        Exercise savedExercise = exerciseRepository.save(
                new Exercise(exerciseDTO.getTitle(), exerciseDTO.getLanguage(), exerciseDTO.getRequirement()));
        log.debug("Titel, Programmiersprache und Vorkenntnisse in der Datenbank gespeichert");
        List<ExerciseLink> exerciseLinks = new ArrayList<>();
        exerciseDTO.getLinks().forEach(link -> exerciseLinks.add(new ExerciseLink(link, savedExercise)));
        exerciseLinks.forEach(exerciseLinkRepository::save);
        log.debug("Alle Links wurden gespeichert", savedExercise.getId());
        if (exerciseDTO.getDescription().isEmpty()) {
            log.debug("Die Aufgabe {} hat keine Beschreibung", exerciseDTO.getTitle());
        } else {
            exerciseDescriptionRepository.save(new ExerciseDescription(exerciseDTO.getDescription(), savedExercise));
            log.debug("Beschreibung wurde hinzugefügt", savedExercise);
        }
        if (exerciseDTO.getPrivateInput().isEmpty() || exerciseDTO.getPublicInput().isEmpty()
                || exerciseDTO.getPrivateOutput().isEmpty() || exerciseDTO.getPublicOutput().isEmpty()) {
            log.debug("Aufgabe ist keine SMART Aufgabe");
        } else {
            savedExercise.setPrivateIO(
                    new ArrayList<>(Arrays.asList(exerciseDTO.getPrivateInput(), exerciseDTO.getPrivateOutput())));
            savedExercise.setPublicIO(
                    new ArrayList<>(Arrays.asList(exerciseDTO.getPublicInput(), exerciseDTO.getPublicOutput())));
            exerciseRepository.save(savedExercise);
            if (exerciseDTO.getDescription().isEmpty()) {
                log.debug("Die Aufgabe {} hat keine Code Datei", exerciseDTO.getTitle());
            } else {
                FileObject newFileObject = new FileObject(exerciseDTO.getCodeFile().getName(),
                        exerciseDTO.getCodeFile().getData(), exerciseDTO.getCodeFile().getType());
                newFileObject.setExercise(savedExercise);
                fileObjectRepository.save(newFileObject);
                log.debug("Code Datei mit dem Name {} wurde gespeichert", exerciseDTO.getCodeFile().getName());
            }
        }
    }
}
