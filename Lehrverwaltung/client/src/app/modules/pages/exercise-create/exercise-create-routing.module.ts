import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {ExerciseCreatePage} from './exercise-create.page';

const routes: Routes = [
  {
    path: '',
    component: ExerciseCreatePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ExerciseCreatePageRoutingModule {
}
