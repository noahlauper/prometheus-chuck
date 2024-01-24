import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ExerciseInfoPageRoutingModule } from './exercise-info-routing.module';

import { ExerciseInfoPage } from './exercise-info.page';
import {ExerciseCreatePageModule} from '../exercise-create/exercise-create.module';
import {RunComponent} from '../../components/run/run.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        ExerciseInfoPageRoutingModule,
        ExerciseCreatePageModule
    ],
    declarations: [ExerciseInfoPage, RunComponent]
})
export class ExerciseInfoPageModule {}
