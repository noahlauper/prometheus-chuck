import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {ModalService} from '../../../core/services/modal/modal.service';
import {ToastService} from '../../../core/services/toast/toast.service';
import {ExerciseModel} from '../../../core/models/exercise/ExerciseModel';
import {InputCustomEvent} from '@ionic/angular';
import {AuthService} from '../../../core/services/auth/auth.service';
import {SearchPipe} from '../../../shared/pipes/search.pipe';
import {ExerciseService} from '../../../core/http/exercise/exercise.service';


@Component({
  selector: 'app-exercises',
  templateUrl: './exercises.page.html',
  styleUrls: ['./exercises.page.scss'],
})
export class ExercisesPage implements OnInit {

  allExercises: ExerciseModel[];
  allRequirements: string[];
  searchInput: string;
  showOnlySMARTExercises: boolean;

  constructor(
    private router: Router,
    private http: HttpClient,
    public modalService: ModalService,
    private exerciseService: ExerciseService,
    private toastService: ToastService,
    public authService: AuthService
  ) {
    this.allExercises = [];
    this.allRequirements = [];
    this.searchInput = '';
    this.showOnlySMARTExercises = false;
  }

  ngOnInit() {
    this.getExercises();
  }

  createExercise() {
    this.modalService.openExerciseCreateComponent(this.allRequirements).then((modal: HTMLIonModalElement) => {
        modal.present();
        modal.onDidDismiss()
          .then((data) => {
            if (data['data']) {
              this.getExercises();
            }
          });
      }
    )
  }

  search(event: Event): void {
    const inputEvent: InputCustomEvent = event as InputCustomEvent;
    if (inputEvent.detail.value) {
      this.searchInput = inputEvent.detail.value;
    } else {
      this.clearInput();
    }
  }

  clearInput() {
    this.searchInput = '';
  }

  getSortedExercises(): ExerciseModel[] {
    return new SearchPipe().transform(this.allExercises, this.searchInput, this.showOnlySMARTExercises)
  }

  private getExercises() {
    this.exerciseService.getExercise().then((exercises: ExerciseModel[]) => {
      this.allExercises = exercises;
      for (const exercise of exercises) {
        if (!this.allRequirements.includes(exercise.requirement)) {
          this.allRequirements.push(exercise.requirement);
        }
      }
      this.allExercises.sort(() => Math.random() - 0.5);
    }).catch(() => {
      this.toastService.createToast('sad-outline', 'Aufgabe konnten nicht geladen werden', 'danger', 5000).then();
    });
  }

}
