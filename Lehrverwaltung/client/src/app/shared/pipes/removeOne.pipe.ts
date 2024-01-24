import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'removeOne'
})
export class RemoveOnePipe implements PipeTransform {

  transform(values: string[], remove: string) {
    const removed: string[] = [];
    return values.filter(value => {
      return value !== remove ? removed.push(value) : null;
    });
  }
}
