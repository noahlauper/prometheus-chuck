import {Component, Input} from '@angular/core';
import {RunModel} from "../../../core/models/run/RunModel";
import {saveAs} from "file-saver";
import {ToastService} from "../../../core/services/toast/toast.service";

@Component({
  selector: 'app-run',
  templateUrl: './run.component.html',
  styleUrls: ['./run.component.scss'],
})
export class RunComponent {

  @Input()
  runModel?: RunModel;

  constructor(private toastService: ToastService) {
  }
  saveFile() {
    if(this.runModel) {
      saveAs(new Blob([atob(this.runModel.data)], {type: this.runModel.type}), this.runModel.name);
      this.toastService.createToast('checkmark-outline', 'Code Datei wird heruntergeladen', 'secondary', 2500);
    }
  }
}
