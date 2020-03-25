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

  seller: Seller = {};
  isEdit: boolean = false;

  constructor(private router: Router,
    private sellerService: SellerAPIService,
    private toaster: ToasterService) { }

  ngOnInit() {
    if (this.router.url.includes("define/seller/edit")) {
      this.ressurect(this.router.url.split("/").slice(-1)[0] );
    }
  }

  ressurect(id: string) {
    this.sellerService.getSeller(+id).subscribe(res => {
      this.seller = res;
      this.isEdit = true;
    })
  }

  createSeller() {
    this.sellerService.createSeller(this.seller).subscribe(res => {
      if (res) {
        this.goHome();
      } else {
        this.toaster.createToast("There was an issue creating the Seller. Make sure all fields are valid.", ToastType.DANGER);
      }
    });
  }

  saveChanges() {
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

  cancel() {
    this.goHome();
  }

  goHome() {
    this.router.navigateByUrl("define/seller/list");
  }
 
}
