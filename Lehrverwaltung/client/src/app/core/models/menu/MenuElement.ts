export class MenuElement {
  menuElementId: number;
  menuElementName: string;
  menuElementRouterLink: string;
  menuElementIcon: string;

  constructor(menuElementId: number, menuElementName: string, menuElementRouterLink: string, menuElementIcon: string) {
    this.menuElementId = menuElementId;
    this.menuElementName = menuElementName;
    this.menuElementRouterLink = menuElementRouterLink;
    this.menuElementIcon = menuElementIcon;
  }
}
