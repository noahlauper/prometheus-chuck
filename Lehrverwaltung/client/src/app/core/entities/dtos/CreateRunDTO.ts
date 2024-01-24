import {FileObject} from '../../models/file/FileObject';

export class CreateRunDTO {

  exerciseId: number;
  username: string;
  codeFile: FileObject;

  constructor(exerciseId: number, username: string, codeFile: FileObject) {
    this.exerciseId = exerciseId;
    this.username = username;
    this.codeFile = codeFile
  }
}
