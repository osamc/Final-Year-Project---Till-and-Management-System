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

  //The list of sellers to show within the component
  sellers: Seller[] = [];

  /**
   * @param sellerAPI used to get the list of sellers
   * @param toaster to raise toasts within the application
   * @param router to navigate the user around the app
   */
  constructor(private sellerAPI: SellerAPIService,
    private toaster:ToasterService,
    private router: Router) { }

  ngOnInit() {
    this.updateView();
  }

  //Gets the list of sellers
  updateView(): void {
    this.sellerAPI.getSellers().subscribe(res => {
      this.sellers = res;
    })
  }

  //Navigates to the create seller section
  createSeller(): void {
    this.router.navigateByUrl('define/seller/create');
  }

  //Navigates to the edit seller section
  editSeller(id: any): void {
    this.router.navigateByUrl('define/seller/edit/' + id);
  }

  //Deletes the selected Seller
  deleteSeller(seller: Seller): void {
    this.sellerAPI.deleteSeller(seller).subscribe(res => {
      this.updateView();
    }, err => {
      this.toaster.createToast("Unable to delete user", ToastType.DANGER);
    });
  }

}
