import {Component} from '@angular/core';
import {UserModel} from '../../../core/models/user/UserModel';
import {SitesList} from '../../../core/models/mystery/MYSTERY';
import {AuthService} from '../../../core/services/auth/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  currentUser: UserModel;
  website: string;

  constructor(private authService: AuthService) {
    this.website = '';
    this.currentUser = new UserModel('', '')
    this.authService.currentUser.subscribe(user => this.currentUser = user);
  }

  loadRandomBackground(): void {
    this.website = SitesList[Math.trunc(Math.random() * Math.floor(SitesList.length - 1))];
  }

}
