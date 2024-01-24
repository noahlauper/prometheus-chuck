import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {AlertController} from '@ionic/angular';
import {ButtonColorService} from '../../../core/services/button-color/button-color.service';
import {ToastService} from '../../../core/services/toast/toast.service';
import {UserInfoModel} from '../../../core/models/userInfo/UserInfoModel';
import {UserModel} from '../../../core/models/user/UserModel';
import {ValidatorsService} from '../../../shared/validators/validators.service';
import {credentialsValidator, passwordValidator} from '../../../core/models/validator/VALIDATORS_LIST';
import {ChangeCurrentUserDTO} from '../../../core/entities/dtos/ChangeCurrentUserDTO';
import {CreateUserDTO} from '../../../core/entities/dtos/CreateUserDTO';
import {AuthService} from '../../../core/services/auth/auth.service';
import {ChangeUserDTO} from '../../../core/entities/dtos/ChangeUserDTO';
import {UserService} from '../../../core/http/user/user.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.page.html',
  styleUrls: ['./settings.page.scss'],
})
export class SettingsPage implements OnInit {

  status: boolean;
  passwordType: string;
  passwordIcon: string;
  names: string[];
  usersInfo: UserInfoModel[];
  currentUser: UserModel;
  settings: 'changePassword' | 'changeCurrentUserData' | 'createUser' | 'deleteUser' | 'changeUserValues' | 'changeUserPassword' | '';
  changeCurrentUserData: FormGroup;
  changeCurrentUserPassword: FormGroup;
  createNewUser: FormGroup;
  changeUserPassword: FormGroup;
  changeUserValues: FormGroup;

  constructor(public buttonColorService: ButtonColorService,
              public validatorsService: ValidatorsService,
              private fb: FormBuilder,
              private http: HttpClient,
              private alertController: AlertController,
              private userService: UserService,
              private toastService: ToastService,
              private authenticationService: AuthService) {
    this.usersInfo = [];
    this.currentUser = new UserModel('', '');
    this.settings = '';
    this.status = false;
    this.passwordIcon = 'eye';
    this.passwordType = 'text';
    this.names = [];
    this.authenticationService.currentUser.subscribe(user => this.currentUser = user);

    this.changeCurrentUserPassword = this.fb.group(
      {
        password: ['', passwordValidator],
        passwordConfirm: ['', passwordValidator],
      },
      {
        validators: this.validatorsService.passwordMatch({notMatching: true})
      }
    );

    this.changeCurrentUserData = this.fb.group(
      {
        name: ['', credentialsValidator],
        surname: ['', credentialsValidator]
      }
    );

    this.changeUserPassword = this.fb.group(
      {
        password: ['', passwordValidator],
        passwordConfirm: ['', passwordValidator],
        username: ['', credentialsValidator]
      },
      {
        validators: this.validatorsService.passwordMatch({notMatching: true})
      }
    );

    this.createNewUser = this.fb.group(
      {
        vorname: ['', credentialsValidator],
        nachname: ['', credentialsValidator],
        username: ['', credentialsValidator],
        status: ['Lehrling'],
        password: ['', passwordValidator],
        passwordConfirm: ['', passwordValidator],
      },
      {
        validators: this.validatorsService.passwordMatch({notMatching: true})
      }
    );

    this.changeUserValues = this.fb.group(
      {
        selectedId: ['', Validators.required],
        name: ['', credentialsValidator],
        surname: ['', credentialsValidator],
        username: ['', credentialsValidator],
        status: ['', credentialsValidator],
      }
    );
  }

  changeValue(value: 'changePassword' | 'changeCurrentUserData' | 'createUser' | 'deleteUser' | 'changeUserValues' | 'changeUserPassword' | '') {
    this.passwordIcon = 'eye';
    if (this.settings === value) {
      this.settings = '';
    } else {
      this.settings = value;
    }
  }

  ngOnInit(): void {
    if (this.currentUser.role === 'Lehrmeister') {
      this.userService.getNames('all').then((allNames: string[]) => this.names = allNames).catch(error => {
        if (error.status === 400) {
          this.toastService.createToast('alert-outline', 'Ungültiger Parameter', 'danger', 5000).then();
        } else {
          this.toastService.createToast('sad-outline', 'Usernamen konnten nicht geladen werden', 'danger', 50000).then();
        }
      });
      this.userService.getUsersInfo('all').then((usersInfo: UserInfoModel[]) => this.usersInfo = usersInfo).catch(() => {
        this.toastService.createToast('sad-outline', 'Benutzerdaten konnten nicht geladen werden', 'danger', 5000).then();
      });
    }
  }

  passwordShowHide() {
    if (this.passwordIcon === 'eye') {
      this.passwordIcon = 'eye-off';
      this.passwordType = 'text';
    } else {
      this.passwordIcon = 'eye';
      this.passwordType = 'password';
    }
  }

  changeCurrentUserPasswordRequest() {
    this.settings = '';
    this.userService.changeCurrentUserPassword(this.changeCurrentUserPassword.get('password')?.value).then(() => {
      this.toastService.createToast('checkmark-outline', 'Passwort verändert', 'success', 5000).then(() => {
        this.changeUserPassword.reset();
      });
    }).catch(() => this.toastService.createToast('sad-outline', 'Passwort konnte nicht verändert werden', 'danger', 5000).then());
  }

  changeCurrentUserCredentials() {
    this.settings = '';
    this.userService.changeCurrentUserCredentials(new ChangeCurrentUserDTO(this.changeCurrentUserData.get('name')?.value,
      this.changeCurrentUserData.get('surname')?.value)).then(() => {
      this.toastService.createToast('checkmark-outline', 'Userdaten verändert', 'success', 5000).then(() => {
        this.changeCurrentUserData.reset();
      });
    }).catch(() => this.toastService.createToast('sad-outline', 'Userdaten konnten nicht verändert werden', 'danger', 5000)).then();
  }

  deleteUserRequest() {
    this.settings = '';
    this.userService.deleteUser().then(() => this.authenticationService.logout()).catch(() => {
      this.toastService.createToast('sad-outline', 'Benutzer konnte nicht gelöscht werden', 'danger', 5000).then();
    });
  }

  changeUserPasswordRequest() {
    this.settings = '';
    this.userService.changeUserPassword(this.changeUserPassword.get('password')?.value,
      this.changeUserPassword.get('username')?.value).then(() => {
      this.toastService.createToast(
        'checkmark-outline',
        'Passwort von ' + this.changeUserPassword.get('username')?.value + '  verändert',
        'success', 5000).then(() => {
        this.changeUserPassword.reset();
      });
    }).catch(() => this.toastService.createToast('sad-outline', 'Passwort konnte nicht verändert werden', 'warning', 5000));
  }

  createUserRequest() {
    this.settings = '';
    this.userService.createUser(new CreateUserDTO(this.createNewUser.get('vorname')?.value,
      this.createNewUser.get('nachname')?.value, this.createNewUser.get('username')?.value,
      this.createNewUser.get('password')?.value, this.createNewUser.get('status')?.value)).then(() => {
      this.toastService.createToast(
        'checkmark-outline',
        'User: ' + this.createNewUser.get('username')?.value + '  erstellt',
        'success', 5000).then(() => {
        this.changeUserPassword.reset();
        this.createNewUser.reset();
      });
    }).catch(error => {
      if (error.status === 409) {
        this.toastService.createToast('alert-outline', 'Benutzer existiert bereits', 'danger', 5000).then();
      } else {
        this.toastService.createToast('sad-outline', 'Benutzer konnte nicht erstellt werden', 'danger', 50000).then();
      }
    });
  }

  changeCredentialsRequest() {
    this.settings = '';
    this.userService.changeUserCredentials(this.usersInfo[this.changeUserValues.get('selectedId')?.value].username,
      new ChangeUserDTO(this.changeUserValues.get('name')?.value, this.changeUserValues.get('surname')?.value,
        this.changeUserValues.get('username')?.value, this.changeUserValues.get('status')?.value)).then(() => {
      this.toastService.createToast(
        'checkmark-outline',
        'Userdaten von ' + this.changeUserValues.get('username')?.value + ' verändert',
        'success', 5000).then(() => {
        this.changeUserValues.reset();
        this.userService.getUsersInfo('all').then((userInfos: UserInfoModel[]) => this.usersInfo = userInfos).catch(() => {
          this.toastService.createToast('sad-outline', 'Userinformationen konnten nicht geladen werden', 'danger', 5000).then();
        });
      });
    }).catch(error => {
      if (error.status === 409) {
        this.toastService.createToast('alert-outline', 'Benutzer existiert bereits', 'warning', 5000).then();
      } else {
        this.toastService.createToast('sad-outline', 'Benutzer konnte nicht verändert werden', 'warning', 5000).then();
      }
    });
  }

  changeUserSelection() {
    const id: number = this.changeUserValues.get('selectedId')?.value;
    this.changeUserValues.get('name')?.setValue(this.usersInfo[id].firstname);
    this.changeUserValues.get('surname')?.setValue(this.usersInfo[id].lastname);
    this.changeUserValues.get('username')?.setValue(this.usersInfo[id].username);
    this.changeUserValues.get('status')?.setValue(this.usersInfo[id].role);
  }


  async presentAlertMultipleButtons() {
    const alert = await this.alertController.create({
      header: 'Warnung',
      message: '<strong>Nachdem du deinen Nutzer löschst, verlierst du alle deine Daten<strong>',
      cssClass: 'alert',
      buttons: [
        {
          text: 'JA',
          handler: () => {
            this.deleteUserRequest();
          }
        },
        {
          text: 'NEIN'
        }
      ],
      mode: 'ios'
    });
    await alert.present();
  }
}
