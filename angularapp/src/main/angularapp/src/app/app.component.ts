import { Component, enableProdMode, OnDestroy, HostListener, OnInit, AfterViewInit } from '@angular/core';
import { Router, ResolveEnd } from '@angular/router';
import { SidebarService } from './services/sidebar.service';
import { environment } from 'src/environments/environment';
import { JWTUserAPIService } from './openapi';
import { TokenService } from './services/token.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

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
    private tokenService: TokenService,
    private jwtService: JWTUserAPIService,
    private modal: NgbModal) {
      //Every time we resolve to an end point, we want to ensure
      //that the user is logged in
      this.router.events.subscribe(evt => {
        if (evt instanceof ResolveEnd)
        this.tokenService.testToken();
      });
  }

  @HostListener('window:unload', [ '$event' ])
  ngOnDestroy(): void {
    this.tokenService.saveToken();
  }
 
  logout() {
    this.tokenService.clearToken();
  }

}
