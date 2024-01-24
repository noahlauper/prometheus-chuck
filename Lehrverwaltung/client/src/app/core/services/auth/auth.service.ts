import {Injectable} from '@angular/core';
import {LoginDTO} from '../../entities/dtos/LoginDTO';
import {BehaviorSubject, lastValueFrom, Observable, tap} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {UserModel} from '../../models/user/UserModel';
import {backend} from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public currentUser: Observable<UserModel>;
  private currentUserSubject: BehaviorSubject<UserModel>;

  constructor(private http: HttpClient, private router: Router) {
    const user: string | null = localStorage.getItem('currentUser');
    if (user) {
      this.currentUserSubject = new BehaviorSubject<UserModel>(JSON.parse(user));
    } else {
      this.currentUserSubject = new BehaviorSubject<UserModel>(new UserModel('', ''));
    }
    this.currentUser = this.currentUserSubject.asObservable()
  }

  public isUserLoggedIn(): boolean {
    if (this.currentUserSubject.value) {
      if (this.currentUserSubject.value.role !== '' || this.currentUserSubject.value.username !== '') {
        return true;
      }
    }
    return false;
  }

  public loginUser(loginDTO: LoginDTO): Observable<UserModel> {
    return this.http.post<UserModel>(backend.ip + 'user-management/login', loginDTO).pipe(tap((userDto: any) => {
      if (userDto) {
        localStorage.setItem('currentUser', JSON.stringify(userDto));
        this.currentUserSubject.next(userDto);
      }
      return userDto;
    }));
  }

  public getUserRole(): string {
    return this.currentUserSubject.value.role;
  }

  public getUsername(): string {
    return this.currentUserSubject.value.username;
  }

  public isUserLehrmeister(): boolean {
    return this.getUserRole() === 'Lehrmeister'
  }

  public logout(): void {
    lastValueFrom(this.http.get<void>(backend.ip + 'user-management/logout')).then(() => {
      this.currentUserSubject.next(new UserModel('', ''));
      localStorage.removeItem('currentUser');
      this.router.navigate(['/login']).then(() => {
        window.location.reload();
      });
    });
  }
}
