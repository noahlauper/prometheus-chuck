export class RunModel {
  username: string;

  date: string;

  runtime: number;

  name: string;
  data: string;
  type: string;

  constructor(username: string, date: string, runtime: number, name: string, data: string, type: string) {
    this.username = username;
    this.date = date;
    this.runtime = runtime;
    this.name = name;
    this.data = data;
    this.type = type;
  }
}
