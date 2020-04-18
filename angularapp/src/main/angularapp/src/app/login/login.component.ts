import { Component, OnInit } from '@angular/core';
import { TokenService } from '../services/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  //Local variables used within the form
  //they are nessacary for angular form validation
  username: string = "";
  pass: string = "";

  /**
   * @param tokenService the token service used to login
   */
  constructor(private tokenService: TokenService) { }

  //Allows the user to login
  login(): void{
    this.tokenService.login(this.username, this.pass);
  }

}
