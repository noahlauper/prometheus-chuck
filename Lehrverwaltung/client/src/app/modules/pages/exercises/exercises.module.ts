import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

import {IonicModule} from '@ionic/angular';

import {ExercisesPageRoutingModule} from './exercises-routing.module';

import {ExercisesPage} from './exercises.page';
import {PipesModule} from '../../../shared/pipes/pipes.module';
import {CardComponent} from "../../components/card/card.component";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ExercisesPageRoutingModule,
    PipesModule
  ],
    declarations: [ExercisesPage, CardComponent]
})
export class ExercisesPageModule {
}
