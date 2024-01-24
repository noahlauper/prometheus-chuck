import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {AuthService} from '../services/auth/auth.service';
import {ToastService} from '../services/toast/toast.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private authService: AuthService, private toastService: ToastService) {
  }

  canActivate(): boolean {
    if (this.authService.isUserLoggedIn()) {
      return true;
    }
    this.toastService.createToast('sad-outline', 'Bitte einloggen', 'warning', 5000).then(() =>
      this.router.navigate(['/login']).then());
    return false;
  }

}
