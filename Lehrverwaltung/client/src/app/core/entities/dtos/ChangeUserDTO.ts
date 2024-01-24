export class ChangeUserDTO {
  firstname: string;
  lastname: string;
  username: string;
  role: string;

  constructor(firstname: string, lastname: string, username: string, role: string) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.role = role;
  }
}
