import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SidebarService {

  //Boolean for keeping track if the side bar should be shown on a page
  public isSidebarEnabled: boolean = true;

  //Boolean used for keeping track if the burger icon should be shown
  public burgerIconHidden: boolean = false;

  constructor() { }

  //Toggles the sidebar being shown
  toggleSideBar(): void {
    this.isSidebarEnabled = !this.isSidebarEnabled;
  }

  //Toggles the burger icon being shown
  toggleBurgerIcon(): void {
    this.burgerIconHidden = !this.burgerIconHidden;
  }

  //Sets the burger icon being shown
  setShowBurgerIcon(shownFlag: boolean): void {
    this.burgerIconHidden = !shownFlag;
  }

    //Sets the sidebar being shown
  setShowSideBar(shownFlag: boolean): void {
    this.isSidebarEnabled = shownFlag;
  }


}
