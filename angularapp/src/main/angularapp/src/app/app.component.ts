import { Component, enableProdMode, OnDestroy, HostListener } from '@angular/core';
import { Router, ResolveEnd } from '@angular/router';
import { SidebarService } from './services/sidebar.service';
import { environment } from 'src/environments/environment';
import { JWTUserAPIService } from './openapi';
import { TokenService } from './services/token.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnDestroy {
  
  //The title shown within the application
  title = 'Till Application'; 

  constructor(private router: Router,
    public sidebar: SidebarService,
    private tokenService: TokenService) {
      this.router.events.subscribe(evt => {
        if (evt instanceof ResolveEnd)
        this.tokenService.testToken();
      });
  }

  @HostListener('window:unload', [ '$event' ])
  ngOnDestroy(): void {
    this.tokenService.saveToken();
  }
 

}
