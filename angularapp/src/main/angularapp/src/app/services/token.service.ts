import { Injectable, Optional, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BASE_PATH, JWTUserAPIService, JwtUser} from '../openapi';
import { Router } from '@angular/router';
import { ToasterService, ToastType } from '../toaster/toaster.service';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  //variable used for the basepath
  private basePath = '';
  //variable used to define default headers
  private defaultHeaders = new HttpHeaders();

  /**
   * @param httpClient used to create and make http requests
   * @param basePath a injectable variable used to determine the basepath
   * @param router used to navigate the user
   * @param tokenAPI used to test the tokens
   * @param toaster used to raise toasts
   */
  constructor(private httpClient: HttpClient, 
    @Optional() @Inject(BASE_PATH) basePath: string,
    private router: Router,
    private tokenAPI: JWTUserAPIService,
    private toaster: ToasterService) {
    if (basePath) {
      this.basePath = basePath;
    }

    let token = localStorage.getItem('btoken');

    if (token) {
      this.tokenAPI.configuration.accessToken = token;
    }
  }

  //Tests if a token is valid
  testToken(): void {
    this.tokenAPI.tokenTest().subscribe(()=> {}, err => {
      this.navigateToLogin();
    })
  }

  //Navigates the user to the login screen
  navigateToLogin() {
    this.router.navigateByUrl('login');
  }

  //Save the token locally
  saveToken(){
    localStorage.setItem('btoken', this.tokenAPI.configuration.accessToken.toString());
  }

  clearToken() {
    localStorage.removeItem('btoken');
    this.tokenAPI.configuration.accessToken = "";
    this.testToken();
  }

  //Logs in the user
  login(username: string, password: string) {
    let user: JwtUser = {username: username, password: password};

    let headers = this.defaultHeaders;
    headers.set('Content-Type', 'application/json');

    //Create a post request to login the user
    this.httpClient.request<any>('post', this.basePath + "/login", {body: user, observe: 'response'}).subscribe(res => {
      let auth = res.headers.get('Authorization');
      this.tokenAPI.configuration.accessToken = auth.replace('Bearer ', '');
      this.router.navigateByUrl('dashboard');
    }, err => {
      this.toaster.createToast('There was an issue, please check that the credentials are correct.', ToastType.DANGER);
    })
  }

}
