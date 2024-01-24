import {NgModule} from '@angular/core';
import {SearchPipe} from './search.pipe';
import {AutoCompletePipe} from './autocomplete.pipe';
import {RemoveOnePipe} from './removeOne.pipe';
import {HttpPipe} from './http.pipe';

@NgModule({
  declarations: [
    SearchPipe,
    AutoCompletePipe,
    RemoveOnePipe,
    HttpPipe
  ],
  exports: [
    SearchPipe,
    AutoCompletePipe,
    RemoveOnePipe,
    HttpPipe
  ]
})
export class PipesModule {
}
