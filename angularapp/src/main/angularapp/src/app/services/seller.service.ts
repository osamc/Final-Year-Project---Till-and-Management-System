import { Injectable } from '@angular/core';
import { Seller, SellerAPIService } from '../openapi';
import { ToasterService, ToastType } from '../toaster/toaster.service';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SellerService {

  //The sellers to keep track of, creating a behavior subject
  //allows for the seller to be a subscribable event
  activeSeller: BehaviorSubject<Seller>;
  previousSeller: Seller;

  //The code used to login
  loginCode: string = " ";

  /**
   * @param sellerService used to login the seller
   * @param toaster  used to raise toasts
   */
  constructor(private sellerService: SellerAPIService,
    private toaster: ToasterService) {
    //by using a behaviour subject we are able to notify apps when
    //the seller changes
    this.activeSeller = new BehaviorSubject<Seller>(null);
    this.previousSeller = null;

    if (this.activeSeller.value) {
      console.log("null is a value");
    }

  }

  //method for dealing with loging in
  login(code: any): void {
    this.sellerService.login(code).subscribe(res => {
      //if we have a seller, then the code was correct
      if (res) {
        //update the sellers
        this.previousSeller = this.activeSeller.value;
        this.activeSeller.next(res);
        this.loginCode = "";
      } else {
        this.toaster.createToast("No user found with that code.", ToastType.DANGER);
      }
    }, err => {
      this.toaster.createToast("No user found with that code.", ToastType.DANGER);
      this.loginCode = "";
    }, () => {

    })
  }

  //Appends a character to the login code
  appendLogin(char: string): void {
    this.loginCode += char;
  }

  //Removes a character from the login code
  backspaceLogin(): void {
    this.loginCode = this.loginCode.slice(0, -1);
  }

  //Logins the user
  submit(): void {
    this.login(this.loginCode.trim());
  }

}
