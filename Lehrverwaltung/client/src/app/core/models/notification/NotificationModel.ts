import {STATUS} from "./STATUS";

export class NotificationModel {
  status: STATUS;
  title: string;

  description: string;

  constructor(status: STATUS, title: string, description: string) {
    this.status = status;
    this.title = title;
    this.description = description;
  }
}
