import { Component, OnInit } from '@angular/core';
import { TokenService } from '../services/token.service';
import { SidebarService } from '../services/sidebar.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  //Local variables used within the form
  //they are nessacary for angular form validation
  username: string = "";
  pass: string = "";

  /**
   * @param tokenService the token service used to login
   */
  constructor(private tokenService: TokenService,
    private sideBar: SidebarService) { }
  
    
  ngOnInit(): void {
    this.sideBar.setShowBurgerIcon(true);
    this.sideBar.setShowSideBar(false);
  }

  //Allows the user to login
  login(): void{
    this.tokenService.login(this.username, this.pass);
  }

}
