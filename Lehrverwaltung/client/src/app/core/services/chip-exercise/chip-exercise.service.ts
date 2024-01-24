import {Injectable} from '@angular/core';
import {EXERCISE_CHIP_OBJECTS} from '../../models/exercise/EXERCISE_CHIP_OBJECTS';
import {ExerciseChipObject} from "../../models/exercise/ExerciseChipObject";

@Injectable({
  providedIn: 'root'
})
export class ChipExerciseService {

  private value: string = '';


  get(language: string, type: 'color' | 'icon' | 'extension' | 'langauge'): string {
    EXERCISE_CHIP_OBJECTS.forEach((chipValue: ExerciseChipObject) => {
      if (language.toUpperCase() === chipValue.language.toUpperCase()) {
        switch (type) {
          case 'color': {
            this.value = chipValue.color
            break;
          }
          case 'icon': {
            this.value = chipValue.icon
            break;
          }
          case 'extension': {
            this.value = chipValue.extension
            break;
          }
          default: {
            this.value = chipValue.language;
            break;
          }
        }
      }
    });
    return this.value;
  }
}
