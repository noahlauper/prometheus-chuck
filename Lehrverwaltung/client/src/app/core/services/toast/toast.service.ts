import {Injectable} from '@angular/core';
import {ToastController} from '@ionic/angular';
import {PredefinedColors, ToastIcons} from '../../models/types/TYPES';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  constructor(private toastController: ToastController) {
  }

  async createToast(icon: ToastIcons, text: string, color: PredefinedColors, duration: number) {
    const toast = await this.toastController.create({
      message: text,
      color,
      duration,
      animated: true,
      buttons: [
        {
          side: 'start',
          icon,
        }
      ]
    });
    await toast.present();
  }
}
