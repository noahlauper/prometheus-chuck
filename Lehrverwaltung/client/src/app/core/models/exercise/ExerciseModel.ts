export class ExerciseModel {

  id: number;
  title: string;
  language: string;
  requirement: string;
  description: string;
  links: string[];
  textFile: string;
  name: string;
  type: string;
  data: string;

  constructor(id: number, title: string, language: string, requirement: string, description: string, links: string[], textFile: string, name: string, type: string, data: string) {
    this.id = id;
    this.title = title;
    this.language = language;
    this.requirement = requirement;
    this.description = description;
    this.links = links;
    this.textFile = textFile;
    this.name = name;
    this.type = type;
    this.data = data;
  }
}
