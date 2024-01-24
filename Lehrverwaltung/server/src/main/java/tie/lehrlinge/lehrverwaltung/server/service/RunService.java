package tie.lehrlinge.lehrverwaltung.server.service;

import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import tie.lehrlinge.lehrverwaltung.server.exception.ExerciseNotFoundException;
import tie.lehrlinge.lehrverwaltung.server.exception.RunNotFoundException;
import tie.lehrlinge.lehrverwaltung.server.model.NotificationModel;
import tie.lehrlinge.lehrverwaltung.server.model.RunModel;
import tie.lehrlinge.lehrverwaltung.server.model.dto.CreateRunDTO;
import tie.lehrlinge.lehrverwaltung.server.model.entity.*;
import tie.lehrlinge.lehrverwaltung.server.repository.ExerciseRepository;
import tie.lehrlinge.lehrverwaltung.server.repository.FileObjectRepository;
import tie.lehrlinge.lehrverwaltung.server.repository.NotificationRepository;
import tie.lehrlinge.lehrverwaltung.server.repository.RunRepository;
import tie.lehrlinge.lehrverwaltung.server.util.DockerJavaUtil;
import tie.lehrlinge.lehrverwaltung.server.util.FileUtil;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class RunService {

    private final RunRepository runRepository;

    private final ExerciseRepository exerciseRepository;

    private final FileObjectRepository fileObjectRepository;

    private final NotificationRepository notificationRepository;

    public NotificationModel getLatestNotificationForExercise(int exerciseId, String username) {
        log.info("Der Benutzer {} holt die letzte Notifikation für die Aufgabe mit der Id: {}", username, exerciseId);
        Exercise exercise = this.exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException(String.valueOf(exerciseId)));
        log.debug("Aufgabe mit dem Id: {} gefunden", exercise.getId());
        Run run = this.runRepository.findFirstByUsernameAndExerciseOrderByCreationDateDesc(username, exercise)
                .orElseThrow(() -> new RunNotFoundException(String.valueOf(exerciseId)));
        log.debug("Run mit dem Id: {} gefunden", run.getId());
        log.info("Die Notifikation mit dem Id: {} wurde abgeholt", run.getId());
        return new NotificationModel(run.getNotification().status.ordinal(), run.getNotification().getTitle(), run.getNotification().getDescription());
    }

    public List<RunModel> getRankingListView(int exerciseId, String username) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy");
        List<RunModel> runModels = new ArrayList<>();
        Exercise exercise = this.exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException(String.valueOf(exerciseId)));
        log.debug("Aufgabe mit dem Id: {} gefunden", exercise.getId());
        List<Run> runs = getBestRunsForExercise(exercise);
        List<Run> personalRuns = runRepository.findDistinctTop2ByUsernameAndExerciseOrderByCreationDateDesc(username, exercise);
        log.debug("Gefunden: {} Einträge für die Runs in der Datenbank", runs.size());
        combineTwoListsRemoveDuplicatesAndSort(runs, personalRuns).forEach(run -> {
            log.debug("Formatiertes Datum: {}", simpleDateFormat.format(run.getCreationDate()));
            runModels.add(new RunModel(
                    run.getUsername(),
                    simpleDateFormat.format(run.getCreationDate()),
                    run.getRuntime(),
                    run.getCodeFile().getName(),
                    run.getCodeFile().getData(),
                    run.getCodeFile().getType()));
        });
        return runModels;
    }

    public Boolean runSolution(CreateRunDTO createRunDTO) {
        Exercise exercise = exerciseRepository.findById(createRunDTO.getExerciseId())
                .orElseThrow(() -> new ExerciseNotFoundException(String.valueOf(createRunDTO.getExerciseId())));
        FileObject newFileObject = new FileObject(
                createRunDTO.getCodeFile().getName(),
                createRunDTO.getCodeFile().getData(),
                createRunDTO.getCodeFile().getType()
        );
        fileObjectRepository.save(newFileObject);
        Run run = new Run(createRunDTO.getUsername(), exercise, newFileObject);
        this.runRepository.save(run);
        log.info("Run mit dem Id {} wurde erstellt", run.getId());
        newFileObject.setRun(run);
        fileObjectRepository.save(newFileObject);
        log.info("Code Datei mit dem Name {} wurde gespeichert", newFileObject.getName());
        Notification notification = prepareCode(run.getCodeFile(), exercise.getPrivateIO().get(0), exercise.getPrivateIO().get(1), run);
        notification.setRun(run);
        this.notificationRepository.save(notification);
        return false;
    }

    private Notification prepareCode(FileObject codeFile, String input, String output, Run run) {
        log.info("Datei {} wird jetzt vorbereitet", codeFile.getName());
        //Hier wird die Punkte der Datei gelöscht, sodass man auch Angular artige Dateien ausführen kann
        String[] splitFileNameArray = codeFile.getName().split("\\.");
        log.debug("Die Datei wurde {} Elementen zerteilt", splitFileNameArray.length);
        String fileEnding = splitFileNameArray[splitFileNameArray.length - 1];
        log.debug("Der Name und Typ der Datei ist {} und {}", codeFile.getName(), fileEnding);
        switch (fileEnding) {
            case "java":
                log.info("Vorbereitung für Java Datei Ausführung");
                return runCode(codeFile, new String[]{"java", "/usr/src/" + codeFile.getName(), input}, input, output, run);
            case "js":
                log.info("Vorbereitung für JavaScript Datei Ausführung");
                return runCode(codeFile, new String[]{"js", "/usr/src/" + codeFile.getName(), input}, input, output, run);
            case "ts":
                log.info("Vorbereitung für TypeScript Datei Ausführung");
                return runCode(codeFile, new String[]{"ts-node", "/usr/src/" + codeFile.getName(), input}, input, output, run);
            default:
                log.info("Dateityp {} wird nicht unterstützt", fileEnding);
                return new Notification(Status.ERROR, "Nicht unterstütztes Dateityp", "Das Dateityp " + fileEnding + " wird nicht unterstützt");
        }
    }

    private Notification runCode(FileObject codeFile, String[] cmd, String input, String output, Run run) {
        FileUtil.saveFile("F:\\temp\\", codeFile);
        DockerJavaUtil.transferFileToDocker(codeFile.getName());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        log.debug("Der run hat den Input: {} und Output {}", input, output);
        long runtime = 0;
        String result = "";
        String error = "";
        // Der Code wird frei mal abgespielt
        for (int i = 0; i <= 2; i++) {
            long startTime = System.nanoTime();
            log.info("Run Nummer {}", i + 1);
            log.info("Zeitmesser gestartet");
            try {
                log.debug("CMD umgebung wird gestartet");
                String execId = DockerJavaUtil.dockerClient.execCreateCmd(DockerJavaUtil.containerResponse.getId())
                        .withAttachStdout(true)
                        .withAttachStderr(true)
                        .withCmd(cmd)
                        .exec()
                        .getId();
                log.debug("Der Output wird hier abgeholt");
                DockerJavaUtil.dockerClient.execStartCmd(execId)
                        .exec(new ExecStartResultCallback(outputStream, errorStream))
                        .awaitCompletion();
            } catch (DockerException | InterruptedException e) {
                log.error("Problem bei laufen der Datei im GraalVM", e);
                return new Notification(Status.ERROR, "GraalVM Fehler", "GraalVM hat die Datei nicht ausführen können");
            }
            long stopTime = System.nanoTime();
            runtime += (stopTime - startTime) / 1000000;
            log.info("Zeitmesser gestoppt");
            // Nur bei der letzten Ausführung wird der Output gespeichert
            if (i == 0) {
                result = outputStream.toString();
                error = errorStream.toString();
            }
        }
        int finalRuntime = (int) runtime / 3;
        log.info("Die Ausführung dauerte: {}", finalRuntime);
        Notification notification = new Notification();
        log.debug("Der Output von Code: '{}'", result);
        if (output.contentEquals(result)) {
            notification.setStatus(Status.SUCCESS);
            notification.setTitle("Du hast es geschafft!!");
            notification.setDescription("Dein Code ist in " + finalRuntime + "ms durchgelaufen");
        } else {
            notification.setStatus(Status.ERROR);
            if (!error.isEmpty()) {
                log.debug("Exception: '{}'", error);
                notification.setTitle("Etwas ist schiefgelaufen");
                notification.setDescription("Dein Code ist in " + finalRuntime + "ms durchgelaufen aber hat eine Exception gehabt:\n" + error);
            } else {
                notification.setTitle("Falsche Lösung");
                log.debug("Fehlerausgabe: '{}'", result);
                notification.setDescription("Dein Code ist in " + finalRuntime + "ms durchgelaufen, hat aber nicht den richtigen Output geliefert");
            }
        }
        run.setRuntime(finalRuntime);

        return notification;
    }

    private List<Run> getBestRunsForExercise(Exercise exercise) {
        log.debug("Beste Durchläufe werden abgeholt");
        List<Run> allRuns = (List<Run>) this.runRepository.findAll();
        Map<String, Run> bestRunsByUser = new HashMap<>();
        for (Run run : allRuns) {
            if (run.getExercise().equals(exercise)) {
                String username = run.getUsername();
                Run currentBest = bestRunsByUser.get(username);
                if (currentBest == null || currentBest.getRuntime() > run.getRuntime()) {
                    bestRunsByUser.put(username, run);
                }
            }
        }
        return new ArrayList<>(bestRunsByUser.values());
    }

    List<Run> combineTwoListsRemoveDuplicatesAndSort(List<Run> allTogether, List<Run> personalList) {
        log.debug("Neue Liste wird erstellt und die anderen Listen werden zusammengefügt");
        List<Run> combinedList = new ArrayList<>();
        combinedList.addAll(allTogether);
        combinedList.addAll(personalList);
        log.debug("Die neue Liste wird nach den Laufzeiten sortiert");
        Collections.sort(combinedList, Comparator.comparingInt(Run::getRuntime));
        return new ArrayList<>(combinedList);
    }
}
