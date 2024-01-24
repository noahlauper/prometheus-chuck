import {Injectable} from '@angular/core';
import {ExerciseModel} from '../../models/exercise/ExerciseModel';
import {lastValueFrom} from 'rxjs';
import {backend} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {ExerciseDTO} from '../../entities/dtos/ExerciseDTO';

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  constructor(private http: HttpClient) {
  }

  createExercise(exerciseDTO: ExerciseDTO): Promise<void> {
    return lastValueFrom(this.http.post<void>(backend.ip + 'exercise-management/exercises', exerciseDTO));
  }

  getExercise(): Promise<ExerciseModel[]> {
    return lastValueFrom(this.http.get<ExerciseModel[]>(backend.ip + 'exercise-management/exercises'));
  }

}
