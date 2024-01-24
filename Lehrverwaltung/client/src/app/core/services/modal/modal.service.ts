import {Injectable} from '@angular/core';
import {ModalController} from '@ionic/angular';
import {ExerciseCreatePage} from '../../../modules/pages/exercise-create/exercise-create.page';
import {ExerciseInfoPage} from "../../../modules/pages/exercise-info/exercise-info.page";
import {ExerciseModel} from "../../models/exercise/ExerciseModel";

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  constructor(private modalCtrl: ModalController) {
  }

  async openExerciseCreateComponent(value: string[]): Promise<HTMLIonModalElement> {
    return await this.modalCtrl.create({
      component: ExerciseCreatePage,
      componentProps: {
        showValues: value
      }
    });
  }

  async openExerciseInfoComponent(exercise: ExerciseModel) {
    const articleComponent = await this.modalCtrl.create({
      component: ExerciseInfoPage,
      componentProps: {
        exercise
      }
    });
    await articleComponent.present();
  }
}
