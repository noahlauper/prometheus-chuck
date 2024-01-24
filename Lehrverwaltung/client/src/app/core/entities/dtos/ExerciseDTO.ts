import {ProgrammingLanguages} from '../../models/types/TYPES';
import {FileObject} from '../../models/file/FileObject';

export class ExerciseDTO {
  title: string;
  description: string;
  language: ProgrammingLanguages;
  requirement: string;
  links: string[];
  privateInput: string;
  privateOutput: string;
  publicInput: string;
  publicOutput: string;
  codeFile: FileObject;


  constructor(title: string, description: string, language: ProgrammingLanguages, requirement: string, links: string[], privateInput: string, privateOutput: string, publicInput: string, publicOutput: string, codeFile: FileObject) {
    this.title = title;
    this.description = description;
    this.language = language;
    this.requirement = requirement;
    this.links = links;
    this.privateInput = privateInput;
    this.privateOutput = privateOutput;
    this.publicInput = publicInput;
    this.publicOutput = publicOutput;
    this.codeFile = codeFile;
  }
}
