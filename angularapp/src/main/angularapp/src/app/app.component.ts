import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
  title = 'test'; 

  public isSidebarEnabled: boolean = false;

  constructor(router: Router) {
    
  }
 
  toggleSideBar() {
    this.isSidebarEnabled = !this.isSidebarEnabled;
  }


}
