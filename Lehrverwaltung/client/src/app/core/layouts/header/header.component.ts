import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {Platform} from '@ionic/angular';
import {MenuElement} from '../../models/menu/MenuElement';
import {MENU_ELEMENTS} from '../../models/menu/MENU_ELEMENTS';
import {AuthService} from '../../services/auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {

  menuElements: MenuElement[] = MENU_ELEMENTS;

  constructor(public router: Router, public platform: Platform, public authService: AuthService) {
  }

}
