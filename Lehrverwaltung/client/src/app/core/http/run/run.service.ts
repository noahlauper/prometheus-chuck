import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {lastValueFrom} from "rxjs";
import {backend} from "../../../../environments/environment";
import {CreateRunDTO} from "../../entities/dtos/CreateRunDTO";
import {RunModel} from "../../models/run/RunModel";
import {NotificationModel} from "../../models/notification/NotificationModel";

@Injectable({
  providedIn: 'root'
})
export class RunService {

  constructor(private http: HttpClient) {
  }

  getStatus(): Promise<boolean> {
    return lastValueFrom(this.http.get<boolean>(backend.ip + 'run/status'));
  }

  getLeaderboard(exerciseId: number, username: string): Promise<RunModel[]> {
    return lastValueFrom(this.http.get<RunModel[]>(backend.ip + 'run/' + exerciseId + '/leaderboard/' + username));
  }

  getLatestNotification(exerciseId: number, username: string): Promise<NotificationModel> {
    return lastValueFrom(this.http.get<NotificationModel>(backend.ip + 'run/' + exerciseId + '/notification/' + username));
  }

  createRun(createRunDTO: CreateRunDTO): Promise<boolean> {
    return lastValueFrom(this.http.post<boolean>(backend.ip + 'run', createRunDTO));
  }
}
