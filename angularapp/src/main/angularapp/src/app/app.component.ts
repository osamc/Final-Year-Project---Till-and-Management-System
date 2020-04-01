import { Component, enableProdMode } from '@angular/core';
import { Router } from '@angular/router';
import { SidebarService } from './sidebar.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
  //The title shown within the application
  title = 'Till Application'; 

  constructor(private router: Router,
    public sidebar: SidebarService) {
  }
 

}
