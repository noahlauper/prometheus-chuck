import {Component, Input, OnInit} from '@angular/core';
import {ExerciseModel} from '../../../core/models/exercise/ExerciseModel';
import {ChipExerciseService} from '../../../core/services/chip-exercise/chip-exercise.service';
import {ModalController} from '@ionic/angular';
import {ToastService} from '../../../core/services/toast/toast.service';
import {saveAs} from 'file-saver';
import {FileObject} from '../../../core/models/file/FileObject';
import {RunService} from "../../../core/http/run/run.service";
import {CreateRunDTO} from "../../../core/entities/dtos/CreateRunDTO";
import {AuthService} from "../../../core/services/auth/auth.service";
import {NotificationModel} from "../../../core/models/notification/NotificationModel";
import {STATUS} from "../../../core/models/notification/STATUS";
import {RunModel} from "../../../core/models/run/RunModel";

@Component({
  selector: 'app-exercise-info',
  templateUrl: './exercise-info.page.html',
  styleUrls: ['./exercise-info.page.scss'],
})
export class ExerciseInfoPage implements OnInit {

  @Input()
  exercise: ExerciseModel;

  notification: NotificationModel;

  runModels: RunModel[];

  viewSelection: 'normal' | 'smart';

  fileObject: FileObject;


  canUpload: boolean;

  isResetting: boolean;

  constructor(
    public chipExerciseService: ChipExerciseService,
    public modalCtrl: ModalController,
    private toastService: ToastService,
    private runService: RunService,
    private authService: AuthService
  ) {
    this.exercise = new ExerciseModel(0, 'loading', 'loading...', 'PHP',
      'loading', [], '', '', '', '');
    this.viewSelection = 'normal';
    this.runModels = [];
    this.canUpload = true;
    this.isResetting = false;
    this.fileObject = new FileObject('', '', '')
    this.notification = new NotificationModel(STATUS.ERROR, '', '');
  }

  ngOnInit() {
    this.checkIfAnotherSolutionIsRunning();
    this.getLatestNotification();
    this.getLeaderBoard();
    this.continuousChecker();
  }

  fileUploaded(fileObject: FileObject): void {
    this.fileObject = fileObject;
  }

  send() {
    if (this.fileObject.data !== '') {
      this.runService.createRun(
        new CreateRunDTO(this.exercise.id, this.authService.getUsername(), this.fileObject)).then(() => {
        this.isResetting = true;
        this.resetFileObject();
        this.checkIfAnotherSolutionIsRunning();
        this.toastService.createToast('happy-outline', 'Run fertig', 'success', 2500)
          .then();
      }).catch(() => {
        this.toastService.createToast('sad-outline', 'Run konnte nicht gestartet werden', 'danger', 5000).then();
      });
    }
  }

  download(data: string, type: string, name: string) {
    saveAs(new Blob([atob(data)], {type: type}), name);
    this.toastService.createToast('checkmark-outline', 'Datei wird heruntergeladen', 'secondary', 2500);
  }

  // Die Breite ausrechnen mit den grössten Wert und dann für jeden berechnen
  getWidth(runtime: number): number{
    return (100 / this.runModels[this.runModels.length - 1].runtime) * runtime;
  }

  // Jede 15 Sekunden
  private continuousChecker() {
    setInterval(() => {
      this.checkIfAnotherSolutionIsRunning();
    }, 15000)
  }

  private checkIfAnotherSolutionIsRunning() {
    this.runService.getStatus().then((isRunning: boolean) => {
      // Wenn jetzt keine Aufgabe mehr läuft werden die Daten neu geholt
      if (this.canUpload === isRunning) {
        this.getLatestNotification();
        this.getLeaderBoard();
      }
      this.canUpload = !isRunning
    });
  }

  private getLatestNotification() {
    this.runService.getLatestNotification(this.exercise.id, this.authService.getUsername()).then(
      (notification: NotificationModel) => this.notification = notification
    )
  }

  private getLeaderBoard() {
    this.runService.getLeaderboard(this.exercise.id, this.authService.getUsername()).then(
      (runModels: RunModel[]) => this.runModels = runModels
    )
  }

  private resetFileObject() {
    this.fileObject = new FileObject('', '', '');
  }

}
