import {Component, Input} from '@angular/core';
import {ChipExerciseService} from '../../../core/services/chip-exercise/chip-exercise.service';
import {ExerciseModel} from '../../../core/models/exercise/ExerciseModel';
import {ModalService} from '../../../core/services/modal/modal.service';


@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss'],
})
export class CardComponent {

  @Input()
  exercise: ExerciseModel;

  constructor(public chipExerciseService: ChipExerciseService, public modalService: ModalService) {
    this.exercise = new ExerciseModel(0, 'loading', 'PHP', 'Keine',
      'loading', [], '', '', '', '');
  }

}
