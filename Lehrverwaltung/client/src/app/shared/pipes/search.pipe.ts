import {Pipe, PipeTransform} from '@angular/core';
import {ExerciseModel} from '../../core/models/exercise/ExerciseModel';

@Pipe({
  name: 'search'
})
export class SearchPipe implements PipeTransform {

  transform(values: ExerciseModel[], input: string, showOnlySMART: boolean): ExerciseModel[] {
    // if no input given
    if (input === undefined || showOnlySMART === undefined) {
      return values;
    } else {
      let exercisesResult: ExerciseModel[] =
        values.filter(value => {
          const compare: string = input.toLowerCase();
          // values are filtered after importance of the attribute
          return value.language.toLowerCase().includes(compare) ?
            value.title.toLowerCase() : value.title.toLowerCase().includes(compare) ?
              value.requirement.toLowerCase() : value.requirement.toLowerCase().includes(compare);
        });

      if (showOnlySMART) {
        exercisesResult = exercisesResult.filter(value => {
          return value.textFile !== null;
        });
      }
      return exercisesResult;
    }
  }
}
