import { Component, OnInit } from '@angular/core';
import { Seller, SellerAPIService } from 'src/app/openapi';
import { Router } from '@angular/router';
import { ToasterService, ToastType } from 'src/app/toaster/toaster.service';

@Component({
  selector: 'app-seller',
  templateUrl: './seller.component.html',
  styleUrls: ['./seller.component.css']
})
export class SellerComponent implements OnInit {

  //local variable for ngmodel
  seller: Seller = {};
  //boolean flag for edit mode
  isEdit: boolean = false;

  /**
   * @param router used to navigate the user around the application
   * @param sellerService used to create/edit the seller
   * @param toaster used to raise toasts
   */
  constructor(private router: Router,
    private sellerService: SellerAPIService,
    private toaster: ToasterService) { }

  ngOnInit() {
    //if we have edit in the url, then we have to ressurect the seller to be 
    //edited
    if (this.router.url.includes("define/seller/edit")) {
      this.ressurect(this.router.url.split("/").slice(-1)[0] );
    }
  }

  //Get the editor to be edited
  ressurect(id: string): void {
    this.sellerService.getSeller(+id).subscribe(res => {
      this.seller = res;
      this.isEdit = true;
    })
  }

  //Creates a new seller
  createSeller(): void {
    this.sellerService.createSeller(this.seller).subscribe(res => {
      if (res) {
        this.goHome();
      } else {
        this.toaster.createToast("There was an issue creating the Seller. Make sure all fields are valid.", ToastType.DANGER);
      }
    });
  }

  //Save the changes to the seller
  saveChanges(): void {
    if (this.isEdit) {
      this.sellerService.updateSeller(this.seller).subscribe(res => {
        if (res) {
          this.goHome();
        } else {
          this.toaster.createToast("There was an issue updating the Seller. Make sure all fields are valid.", ToastType.DANGER);
        }
      })
    } else {
      this.toaster.createToast("You are not in edit mode.", ToastType.DANGER);
    }
  }

  //cancels the form
  cancel(): void {
    this.goHome();
  }

  //Navigates the user back to the list component
  goHome(): void {
    this.router.navigateByUrl("define/seller/list");
  }
 
}
