import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SidebarService {

  //Boolean for keeping track if the side bar should be shown on a page
  public isSidebarEnabled: boolean = true;

  public burgerIconHidden: boolean = false;

  constructor() { }

  toggleSideBar() {
    this.isSidebarEnabled = !this.isSidebarEnabled;
  }

  toggleBurgerIcon() {
    this.burgerIconHidden = !this.burgerIconHidden;
  }

  setShowBurgerIcon(shownFlag: boolean) {
    this.burgerIconHidden = !shownFlag;
  }

  setShowSideBar(shownFlag: boolean) {
    this.isSidebarEnabled = shownFlag;
  }


}
