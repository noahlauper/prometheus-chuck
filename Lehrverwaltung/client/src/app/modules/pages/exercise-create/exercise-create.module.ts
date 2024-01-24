import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {IonicModule} from '@ionic/angular';

import {ExerciseCreatePageRoutingModule} from './exercise-create-routing.module';

import {ExerciseCreatePage} from './exercise-create.page';
import {PipesModule} from '../../../shared/pipes/pipes.module';
import {FileUploadComponent} from '../../components/file-upload/file-upload.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        ExerciseCreatePageRoutingModule,
        ReactiveFormsModule,
        PipesModule
    ],
    exports: [
        FileUploadComponent
    ],
    declarations: [ExerciseCreatePage, FileUploadComponent]
})
export class ExerciseCreatePageModule {
}
