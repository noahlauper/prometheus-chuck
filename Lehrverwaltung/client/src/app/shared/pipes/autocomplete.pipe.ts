import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'autocomplete'
})
export class AutoCompletePipe implements PipeTransform {

  transform(values: string[], input: string): string[] {

    // if no input given
    if (input === undefined) {
      return [];
    } else {
      const array: string[] = [];
      values.filter(value => {
          // values are filtered after importance of the attribute
          return value.toLowerCase().includes(input.toString().toLowerCase()) ? array.push(value) : null;
        }
      );
      if (array[0]?.toLowerCase() !== input.toString().toLowerCase()) {
        if (array.length >= 5) {
          return array.slice(0, 4);
        } else {
          return array;
        }
      } else {
        return [];
      }
    }
  }
}
