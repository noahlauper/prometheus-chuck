import {Injectable} from '@angular/core';
import {lastValueFrom} from 'rxjs';
import {backend} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {UserInfoModel} from '../../models/userInfo/UserInfoModel';
import {CreateUserDTO} from '../../entities/dtos/CreateUserDTO';
import {ChangeCurrentUserDTO} from '../../entities/dtos/ChangeCurrentUserDTO';
import {ChangeUserDTO} from '../../entities/dtos/ChangeUserDTO';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  createUser(createUserDTO: CreateUserDTO): Promise<void> {
    return lastValueFrom(this.http.post<void>(backend.ip + 'user-management/user', createUserDTO));
  }

  deleteUser(): Promise<void> {
    return lastValueFrom(this.http.delete<void>(backend.ip + 'user-management/user'));
  }

  getNames(parameter: string): Promise<string[]> {
    return lastValueFrom(this.http.get<string[]>(backend.ip + 'user-management/users/usernames?parameter=' + parameter));
  }

  getUsersInfo(parameter?: string): Promise<UserInfoModel[]> {
    if (parameter != null) {
      return lastValueFrom(this.http.get<UserInfoModel[]>(backend.ip + 'user-management/users?parameter=' + parameter));
    }
    return lastValueFrom(this.http.get<UserInfoModel[]>(backend.ip + 'user-management/users'));
  }

  changeCurrentUserPassword(password: string): Promise<void> {
    return lastValueFrom(this.http.put<void>(backend.ip + 'user-management/password?newPassword=' + password, null));
  }

  changeCurrentUserCredentials(changeCurrentUserDTO: ChangeCurrentUserDTO): Promise<void> {
    return lastValueFrom(this.http.put<void>(backend.ip + 'user-management/user', changeCurrentUserDTO));
  }

  changeUserPassword(password: string, username: string): Promise<void> {
    return lastValueFrom(this.http.put<void>(backend.ip + 'user-management/password/' + username + '?newPassword=' + password, null));
  }

  changeUserCredentials(oldUsername: string, changeUserDTO: ChangeUserDTO): Promise<void> {
    return lastValueFrom(this.http.put<void>(backend.ip + 'user-management/user/' + oldUsername, changeUserDTO));
  }
}
