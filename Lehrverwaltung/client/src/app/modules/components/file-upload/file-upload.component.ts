import {Component, ElementRef, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild} from '@angular/core';
import {ToastService} from '../../../core/services/toast/toast.service';
import {FileObject} from '../../../core/models/file/FileObject';
import {FileUtils} from '../../../shared/utils/FileUtils';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss'],
})
export class FileUploadComponent implements OnChanges {


  @ViewChild("file_input")
  private input: ElementRef | undefined;

  @Input()
  extension: string;

  @Input()
  reset: boolean;

  @Output()
  private fileUploadEmitter: EventEmitter<FileObject> = new EventEmitter();

  fileObject: FileObject | undefined;

  constructor(private toastService: ToastService) {
    this.extension = 'java'
    this.reset = false;
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['reset']) {
      this.resetValues();
      this.reset = false;
    }
  }

  onDragEvent(event: Event) {
    event.preventDefault();
    event.stopPropagation();
  }

  onDrop(dragEvent: DragEvent) {
    this.onDragEvent(dragEvent)
    if (dragEvent.dataTransfer) {
      this.readFile(dragEvent.dataTransfer.files[0]);
    }
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files) {
      this.readFile(input.files[0]);
    }
  }

  readFile(file: File) {
    if (file) {
      if (this.checkIfFileTypeIsCorrect(file.name)) {
        this.toastService.createToast(
          'checkmark-outline',
          'Datei ' + this.fileObject?.name + ' wurde abgespeichert',
          'success',
          2500).then(() => {
          const reader = new FileReader();
          reader.onload = () => {
            const arrayBuffer = reader.result as ArrayBuffer;
            this.fileObject = new FileObject(file.name, file.type, FileUtils.binaryToString(new Uint8Array(arrayBuffer)))
            this.fileUploadEmitter.emit(this.fileObject);
          };
          reader.onerror = () => {
            this.toastService.createToast(
              'ban-outline',
              'Datei konnte nicht abgespeichert werden',
              'danger',
              2500).then(() => this.fileUploadEmitter.emit(this.fileObject));
          };
          reader.readAsArrayBuffer(file)
        });
      } else {
        this.toastService.createToast(
          'alert-outline',
          'Es muss eine .' + this.extension + ' Datei sein',
          'warning',
          2500).then();
      }
    }
  }

  removeSelectedFile() {
    this.resetValues();
    this.toastService.createToast(
      'checkmark-outline',
      'Die ausgewählte Datei wurde entfernt',
      'success',
      2500).then(() => this.fileUploadEmitter.emit(this.fileObject));
  }

  private checkIfFileTypeIsCorrect(name: string): boolean {
    const nameSplit: string[] = name.split('.');
    return nameSplit[nameSplit.length - 1] === this.extension;
  }

  private resetValues(){
    this.fileObject = new FileObject('', '', '');
    if(this.input) {
      this.input.nativeElement.value = "";
    }
  }

}
