export class CreateUserDTO {
  firstname: string;
  lastname: string;
  username: string;
  password: string;
  role: string;

  constructor(firstname: string, lastname: string, username: string, password: string, role: string) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.password = password;
    this.role = role;
  }
}
