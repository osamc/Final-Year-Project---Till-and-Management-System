import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { SidebarService } from '../sidebar.service';
import { PageAPIService, SellerAPIService, TransactionAPIService, PageInfo } from '../openapi';
import { ReceiptService } from '../receipt.service';
import { SellerService } from '../seller.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TransactionViewComponent } from '../transaction-view/transaction-view.component';

@Component({
  selector: 'app-till',
  templateUrl: './till.component.html',
  styleUrls: ['./till.component.css']
})
export class TillComponent implements OnInit {

  //For the navigation
  pages: PageInfo[] = [];
  page: number = 0;
  //To be shown on the page
  toDisplay: PageInfo[] = [];
  //to represent moving forward in pages
  canIncrease: boolean = false;
  //to represent moving backwards in pages
  canDecrease: boolean = false;

  selectedPage: PageInfo = {};
  productArray: any[] = [[]];

  paymentOptions: Object[] = [];

  infoMode: boolean = false;

  constructor(private sidebarService: SidebarService,
    private pageAPI: PageAPIService,
    private sellerAPI: SellerAPIService,
    private transactionAPI: TransactionAPIService,
    private receiptService: ReceiptService,
    public sellerService: SellerService,
    private modalService: NgbModal,
    private sidebar: SidebarService) { 
    this.sidebarService.setShowBurgerIcon(false);
    this.sidebarService.setShowSideBar(false);
  }

  ngOnInit(): void {  

    this.createPaymentOptions();

    this.pageAPI.getPages().subscribe(res => {
      this.pages = res;
      this.changeNavPage(0);
      this.selectedPage = this.pages[0];
      this.createProductArray();
    });
  }

  createPaymentOptions() {
    this.paymentOptions.push({amount: "500", image: '', display: "£5"});
    this.paymentOptions.push({amount: "1000", image: '', display: "£10"});
    this.paymentOptions.push({amount: "2000", image: '', display: "£20"});
    this.paymentOptions.push({display: "Cash"});
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

  navigate(page: PageInfo) {
    this.selectedPage = page;
    this.createProductArray();
  }

  changeNavPage(incrament: number) {
    if (this.page + incrament >= 0 && (this.page + incrament) * 5 <= this.pages.length) {
      this.page += incrament;
      let startIndex = this.page * 5;
      this.toDisplay = this.pages.slice(startIndex, startIndex + 5);;
    }
  }

  changeSeller(modal: any) {
    let modalRef = this.modalService.open(modal, {size: "lg", centered: true,});
    modalRef.result.then(res => {
      if (res === 'login') {
        this.sellerService.submit();
      }
    })
  }

  openSettings(modal: any) {
    this.modalService.open(modal, {size: "lg"});
  }

  toggleSidebar() {
    this.sidebar.toggleSideBar();
  }

  toggleBurgerIcon() {
    this.sidebar.toggleBurgerIcon();
  }

  loginAppend(char: string) {
    this.sellerService.appendLogin(char);
  }

  loginBackspace() {
    this.sellerService.backspaceLogin();
  }

  loginSubmit() {
    this.sellerService.submit();
  }

  makePayment(value?: any) {
    this.receiptService.payment(value);
  }

  clearTransaction() {
    this.receiptService.clearTransaction();
  }

  toggleInfo() {
    this.infoMode = !this.infoMode;
  }

  viewTransactions() {
    this.modalService.open(TransactionViewComponent, {size: "lg"});
  }

}
