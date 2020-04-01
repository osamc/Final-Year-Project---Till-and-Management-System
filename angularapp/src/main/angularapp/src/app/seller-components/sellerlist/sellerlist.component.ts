import { Component, OnInit } from '@angular/core';
import { Seller, SellerAPIService } from 'src/app/openapi';
import { ToasterService, ToastType } from 'src/app/toaster/toaster.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sellerlist',
  templateUrl: './sellerlist.component.html',
  styleUrls: ['./sellerlist.component.css']
})
export class SellerlistComponent implements OnInit {

  sellers: Seller[] = [];

  constructor(private sellerAPI: SellerAPIService,
    private toaster:ToasterService,
    private router: Router) { }

  ngOnInit() {
    this.updateView();
  }

  //Gets the list of sellers
  updateView() {
    this.sellerAPI.getSellers().subscribe(res => {
      this.sellers = res;
    })
  }

  //Navigates to the create seller section
  createSeller() {
    this.router.navigateByUrl('define/seller/create');
  }

  //Navigates to the edit seller section
  editSeller(id: any) {
    this.router.navigateByUrl('define/seller/edit/' + id);
  }

  //Deletes the selected Seller
  deleteSeller(seller: Seller) {
    this.sellerAPI.deleteSeller(seller).subscribe(res => {
      this.updateView();
    }, err => {
      this.toaster.createToast("Unable to delete user", ToastType.DANGER);
    });
  }

}
