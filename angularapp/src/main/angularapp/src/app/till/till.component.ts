import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { SidebarService } from '../sidebar.service';
import { PageAPIService, SellerAPIService, TransactionAPIService, PageInfo } from '../openapi';
import { ReceiptService } from '../receipt.service';
import { typeWithParameters } from '@angular/compiler/src/render3/util';

@Component({
  selector: 'app-till',
  templateUrl: './till.component.html',
  styleUrls: ['./till.component.css']
})
export class TillComponent implements OnInit {

  pages: PageInfo[] = [];
  selectedPage: PageInfo = {};
  productArray: any[] = [[]];

  first: boolean = true;

  constructor(private sidebarService: SidebarService,
    private pageAPI: PageAPIService,
    private sellerAPI: SellerAPIService,
    private transactionAPI: TransactionAPIService,
    private receiptService: ReceiptService) { 
    this.sidebarService.setShowBurgerIcon(false);
    this.sidebarService.setShowSideBar(false);
  }

  ngOnInit(): void {  
    this.pageAPI.getPages().subscribe(res => {
      this.pages = res;
      this.selectedPage = this.pages[0];
      this.createProductArray();
    });
  }

 
  createProductArray() {
    this.productArray.length = this.selectedPage.yrows;

    for(let i = 0; i < this.productArray.length; i++) {
      this.productArray[i] = [];
      this.productArray[i].length = this.selectedPage.xrows;
    }

    this.selectedPage.productAssociations.forEach(association => {
      this.productArray[association.y][association.x] = association.product;
    })
  }

}
