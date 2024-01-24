export class FileObject {
  name: string;
  type: string;
  data: string;

  constructor(name: string, type: string, data: string) {
    this.name = name;
    this.type = type;
    this.data = data;
  }
}
