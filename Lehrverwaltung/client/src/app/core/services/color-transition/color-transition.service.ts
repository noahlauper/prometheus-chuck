import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ColorTransitionService {

  static reformatColorArrayToHex(rgbColors: number[]): string {
    let hexR: string = rgbColors[0].toString(16).split('.')[0];
    let hexG: string;
    let hexB: string;
    hexG = rgbColors[1].toString(16).split('.')[0];
    hexB = rgbColors[2].toString(16).split('.')[0];

    if (hexR.length === 1) {
      hexR = '0' + hexR;
    }
    if (hexG.length === 1) {
      hexG = '0' + hexG;
    }
    if (hexB.length === 1) {
      hexB = '0' + hexB;
    }
    return hexR + '' + hexG + '' + hexB;
  }

  static createColorArray(percentage: number): number[] {
    let red: number;
    let green: number;
    let blue: number;
    if (percentage <= 50) {
      red = 255;
      green = 255 / 100 * (percentage * 2);
      blue = 0;
    } else if (percentage <= 100) {
      red = 255 / 100 * (100 - ((percentage - 50) * 2));
      green = 255;
      blue = 0;
    } else if (percentage <= 150) {
      red = 0;
      green = 255;
      blue = 255 / 100 * ((percentage - 100) * 2);
    } else {
      red = 0;
      if (percentage > 200) {
        green = 0;
      } else {
        green = 255 / 100 * (100 - ((percentage - 150) * 2));
      }
      blue = 255;
    }
    return [red, green, blue];
  }
}
