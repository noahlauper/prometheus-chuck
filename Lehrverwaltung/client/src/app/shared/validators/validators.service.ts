import {Injectable} from '@angular/core';
import {AbstractControl, FormGroup, ValidationErrors, ValidatorFn} from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ValidatorsService {

  private assertion: boolean;

  constructor() {
    this.assertion = false;
  }

  passwordMatch(errorName: ValidationErrors): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const password: string = control.get('password')?.value;
      const passwordConfirm: string = control.get('passwordConfirm')?.value;
      return passwordConfirm === password ? null : errorName;
    };
  }

  checkIfControlHasErrorAndIsTouched(fieldName: string, formGroup: FormGroup): boolean {
    if (formGroup.get(fieldName)) {
      this.assertion = (formGroup.get(fieldName)?.touched && formGroup.get(fieldName)?.hasError('pattern')) as boolean;
      return this.assertion
    } else {
      return false;
    }
  }

  checkIfPasswordsAreNotTheSame(passwordFieldName: string, passwordConfirmFieldName: string, formGroup: FormGroup): boolean {
    this.assertion = (formGroup.get(passwordFieldName)?.value !== formGroup.get(passwordConfirmFieldName)?.value! &&
      formGroup.get(passwordFieldName)?.touched && formGroup.get(passwordConfirmFieldName)?.touched) as boolean;
    return this.assertion;
  }
}
