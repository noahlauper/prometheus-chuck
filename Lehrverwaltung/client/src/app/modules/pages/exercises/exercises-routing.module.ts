import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {ExercisesPage} from './exercises.page';

const routes: Routes = [
    {
        path: '',
        component: ExercisesPage
    },
    {
        path: 'exercise-create',
        loadChildren: () => import('../exercise-create/exercise-create.module').then(m => m.ExerciseCreatePageModule)
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class ExercisesPageRoutingModule {
}
