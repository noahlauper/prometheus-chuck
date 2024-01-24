export class ExerciseChipObject {
  language: string;
  color: string;
  icon: string;
  extension: string;
  isSupported: boolean;


  constructor(language: string, color: string, icon: string, extension: string, isSupported: boolean) {
    this.language = language;
    this.color = color;
    this.icon = icon;
    this.extension = extension;
    this.isSupported = isSupported;
  }
}
