import {Validators} from '@angular/forms';

export const passwordValidator: Validators[] =
  [
    Validators.required,
    Validators.maxLength(65),
    Validators.minLength(8)
  ];

export const credentialsValidator: Validators[] =
  [
    Validators.required,
    Validators.maxLength(25),
    Validators.minLength(2),
    Validators.pattern('^[A-Za-zñÑáéíóúÁÉÍÓÚöÖüÜäÄàÀ ]+$')
  ];

export const numbersValidator: Validators[] =
  [
    Validators.required,
    Validators.maxLength(22),
    Validators.minLength(1),
    Validators.pattern('^[0-9]*$')
  ];
