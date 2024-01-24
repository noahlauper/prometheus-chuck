import {Injectable} from '@angular/core';
import {FormGroup} from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ButtonColorService {

  choseColorForField(fieldName: string, formGroup: FormGroup, mustBeTouched: boolean): string {
    if (formGroup.get(fieldName)?.valid && this.getTouched(fieldName, formGroup, mustBeTouched)) {
      return 'success';
    } else if (!formGroup.get(fieldName)?.valid && this.getTouched(fieldName, formGroup, mustBeTouched) ||
      this.getTouched(fieldName, formGroup, mustBeTouched)) {
      return 'danger';
    } else {
      return 'dark';
    }
  }

  choseColorForMultipleFields(fieldNames: string[], formGroup: FormGroup, mustBeTouched: true) {
    // @ts-ignore
    fieldNames.forEach((fieldName: string) => {
      // Überprüfung, ob der Feld valid ist, berührt wurde und letzte Iteration ist
      if (formGroup.get(fieldName)?.valid
        && this.getTouched(fieldName, formGroup, mustBeTouched)
        && fieldName === fieldNames[fieldNames.length - 1]) {
        return 'success';
      } else if (!formGroup.get(fieldName)?.valid && this.getTouched(fieldName, formGroup, mustBeTouched) ||
        this.getTouched(fieldName, formGroup, mustBeTouched)) {
        return 'danger';
      }
    })
    return 'dark';
  }

  choseColorForButton(formGroup: FormGroup) {
    if (formGroup.valid && formGroup.touched) {
      return 'success';
    } else if (!formGroup.valid && formGroup.touched || formGroup.touched) {
      return 'danger';
    } else {
      return 'dark';
    }
  }

  private getTouched(fieldName: string, formGroup: FormGroup, mustBeTouched: boolean) {
    return mustBeTouched ? formGroup.get(fieldName)?.touched : true;
  }
}
