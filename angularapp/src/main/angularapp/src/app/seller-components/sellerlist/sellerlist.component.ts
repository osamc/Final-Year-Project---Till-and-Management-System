import { Component, OnInit } from '@angular/core';
import { Seller, SellerAPIService } from 'src/app/openapi';
import { ToasterService } from 'src/app/toaster/toaster.service';
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

  updateView() {
    this.sellerAPI.getSellers().subscribe(res => {
      this.sellers = res;
    })
  }

  createSeller() {
    this.router.navigateByUrl('define/seller/create');
  }

  editSeller(id: any) {
    this.router.navigateByUrl('define/seller/edit/' + id);
  }

  deleteSeller(seller: Seller) {
    this.sellerAPI.deleteSeller(seller).subscribe(res => {
      this.updateView();
    }, err => {
      //do something
    });
  }

}
