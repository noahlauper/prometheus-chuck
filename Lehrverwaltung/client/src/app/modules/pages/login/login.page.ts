import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ToastService} from '../../../core/services/toast/toast.service';
import {ButtonColorService} from '../../../core/services/button-color/button-color.service';
import {AuthService} from '../../../core/services/auth/auth.service';
import {LoginDTO} from '../../../core/entities/dtos/LoginDTO';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage {

  loginForm: FormGroup;

  constructor(
    public buttonColorService: ButtonColorService,
    private router: Router,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private route: ActivatedRoute,
    private toastService: ToastService
  ) {
    this.loginForm = this.formBuilder.group({
        username: new FormControl('', Validators.required),
        password: new FormControl('', Validators.required),
      }
    );
  }

  public submitLoginForm() {
    this.authService.loginUser(new LoginDTO(this.loginForm.get('username')?.value, this.loginForm.get('password')?.value))
      .subscribe({
        next: () => {
          this.router.navigate(['/home']).then(() =>
            this.toastService.createToast('checkmark-outline', 'Eingeloggt!', 'success', 500)
          );
        }, error: error => {
          if (error.status === 400) {
            this.toastService.createToast('ban-outline', 'Username oder Passwort falsch!', 'danger', 5000).then();
            this.loginForm.reset();
            this.loginForm.markAllAsTouched();
          } else {
            this.toastService.createToast('ban-outline', 'Server nicht erreichbar!', 'danger', 5000).then();
          }
        }
      });
  }
}
