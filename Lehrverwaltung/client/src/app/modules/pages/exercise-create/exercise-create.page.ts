import {Component, Input} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {ToastService} from '../../../core/services/toast/toast.service';
import {ButtonColorService} from '../../../core/services/button-color/button-color.service';
import {InputCustomEvent, ModalController} from '@ionic/angular';
import {ExerciseChipObject} from '../../../core/models/exercise/ExerciseChipObject';
import {EXERCISE_CHIP_OBJECTS} from '../../../core/models/exercise/EXERCISE_CHIP_OBJECTS';
import {ExerciseDTO} from '../../../core/entities/dtos/ExerciseDTO';
import {AutoCompletePipe} from '../../../shared/pipes/autocomplete.pipe';
import {ChipExerciseService} from '../../../core/services/chip-exercise/chip-exercise.service';
import {FileObject} from '../../../core/models/file/FileObject';
import {ExerciseService} from '../../../core/http/exercise/exercise.service';


@Component({
  selector: 'app-exercise-create',
  templateUrl: './exercise-create.page.html',
  styleUrls: ['./exercise-create.page.scss'],
})
export class ExerciseCreatePage {

  @Input() showValues: string[];

  requirement: string;
  createExerciseForm: FormGroup;
  exerciseChipObjects: ExerciseChipObject[];
  isSMARTExercisePossible: boolean;

  viewSelection: 'normal' | 'smart';


  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    public buttonColorService: ButtonColorService,
    public modalCtrl: ModalController,
    public chipExerciseService: ChipExerciseService,
    private toastService: ToastService,
    private exerciseService: ExerciseService
  ) {
    this.exerciseChipObjects = EXERCISE_CHIP_OBJECTS;
    this.createExerciseForm = this.fb.group({
        title: ['', [Validators.required, Validators.maxLength(24), Validators.minLength(5)]],
        language: ['', [Validators.required, Validators.maxLength(12), Validators.minLength(2)]],
        requirement: ['', [Validators.required, Validators.maxLength(12), Validators.minLength(2)]],
        description: ['', [Validators.maxLength(2500)]],
        publicInput: ['', [Validators.maxLength(2500)]],
        publicOutput: ['', [Validators.maxLength(2500)]],
        privateInput: ['', [Validators.maxLength(2500)]],
        privateOutput: ['', [Validators.maxLength(2500)]],
        codeFile: null,
        links: this.fb.array([])
      }
    );
    this.showValues = [];
    this.requirement = '';
    this.isSMARTExercisePossible = false;
    this.viewSelection = 'normal';
  }

  get linksArray() {
    return this.createExerciseForm.get('links') as FormArray;
  }

  getAllRequirements(): string[] {
    return new AutoCompletePipe().transform(this.showValues, this.requirement);
  }

  getAllLinksStringArray(): string[] {
    const values: string[] = [];
    for (const link of this.linksArray.controls) {
      values.push(link.value.link);
    }
    return values;
  }

  getLinkNumber(): number {
    return this.linksArray.length;
  }

  addLink() {
    this.linksArray.push(this.fb.group({
      link: ['', [Validators.required, Validators.maxLength(255), Validators.minLength(9)]]
    }));
  }


  select(event: Event) {
    const inputEvent: InputCustomEvent = event as InputCustomEvent;
    if (inputEvent.detail.value) {
      this.requirement = inputEvent.detail.value;
    } else {
      this.requirement = '';
    }
  }

  selectFromAutoComplete(value: string): void {
    this.createExerciseForm.get('requirement')?.setValue(value);
  }

  removeLink(index: number) {
    this.linksArray.removeAt(index);
  }

  checkIfExerciseCanBeSMART(): void {
    const selectedExerciseChip: ExerciseChipObject | undefined = this.exerciseChipObjects
      .find(exerciseChip => exerciseChip.language === this.createExerciseForm.get('language')?.value);
    if (selectedExerciseChip?.isSupported) {
      this.isSMARTExercisePossible = true;
    } else {
      this.isSMARTExercisePossible = false;
    }
  }

  fileUploaded(fileObject: FileObject): void {
    this.createExerciseForm.get('codeFile')?.setValue(fileObject);
  }

  submitExercise() {
    this.exerciseService.createExercise(
      new ExerciseDTO(
        this.createExerciseForm.get('title')?.value,
        this.createExerciseForm.get('description')?.value,
        this.createExerciseForm.get('language')?.value,
        this.createExerciseForm.get('requirement')?.value,
        this.getAllLinksStringArray(),
        this.createExerciseForm.get('privateInput')?.value,
        this.createExerciseForm.get('privateOutput')?.value,
        this.createExerciseForm.get('publicInput')?.value,
        this.createExerciseForm.get('publicOutput')?.value,
        this.createExerciseForm.get('codeFile')?.value
      )
    ).then(() => {
      this.toastService.createToast('happy-outline', 'Aufgabe erstellt', 'success', 2500)
        .then(() => this.modalCtrl.dismiss(true));
    }).catch(() => {
      this.toastService.createToast('sad-outline', 'Aufgabe konnte nicht erstellt werden', 'danger', 5000).then();
    });
  }
}
