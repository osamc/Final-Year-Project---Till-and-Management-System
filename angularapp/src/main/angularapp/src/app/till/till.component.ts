import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { SidebarService } from '../services/sidebar.service';
import { PageAPIService, SellerAPIService, TransactionAPIService, PageInfo } from '../openapi';
import { ReceiptService } from '../services/receipt.service';
import { SellerService } from '../services/seller.service';
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

  //the selected page
  selectedPage: PageInfo = {};

  //The product array to display
  productArray: any[] = [[]];

  //Array for containing the payment options
  paymentOptions: Object[] = [];

  //Boolean flag to represent info mode (used to find out more about products)
  infoMode: boolean = false;

  /**
   * @param sidebarService Used to hide/show the sidebar
   * @param pageAPI Used to get the pages to be shown
   * @param transactionAPI Used to create transactions
   * @param receiptService Used to show the reciept
   * @param sellerService Used to login the user
   * @param modalService Used to show modals to the user
   */
  constructor(private sidebarService: SidebarService,
    private pageAPI: PageAPIService,
    private transactionAPI: TransactionAPIService,
    private receiptService: ReceiptService,
    public sellerService: SellerService,
    private modalService: NgbModal) {
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

  //Create the payment options to be shown on the till
  createPaymentOptions(): void {
    this.paymentOptions.push({ amount: "500", display: "£5" });
    this.paymentOptions.push({ amount: "1000", display: "£10" });
    this.paymentOptions.push({ amount: "2000", display: "£20" });
    this.paymentOptions.push({ display: "Cash" });
  }

  //Creates the product array that is used to show the products
  createProductArray(): void {
    this.productArray.length = this.selectedPage.yrows;

    for (let i = 0; i < this.productArray.length; i++) {
      this.productArray[i] = [];
      this.productArray[i].length = this.selectedPage.xrows;
    }

    this.selectedPage.productAssociations.forEach(association => {
      this.productArray[association.y][association.x] = association.product;
    })
  }

  //Selects a new page to be displayed
  navigate(page: PageInfo): void {
    this.selectedPage = page;
    this.createProductArray();
  }

  //Change the page pagenation index
  changeNavPage(incrament: number): void {
    //ensure we don't go above the number of pages or below 0
    if (this.page + incrament >= 0 && (this.page + incrament) * 5 <= this.pages.length) {
      this.page += incrament;
      let startIndex = this.page * 5;
      this.toDisplay = this.pages.slice(startIndex, startIndex + 5);;
    }
  }

  //Opens the login modal and allows the user to change accounts
  changeSeller(modal: any): void {
    let modalRef = this.modalService.open(modal, { size: "lg", centered: true, });
    modalRef.result.then(res => {
      if (res === 'login') {
        this.sellerService.submit();
      }
    })
  }

  //Opens the setting modal
  openSettings(modal: any): void {
    this.modalService.open(modal, { size: "lg" });
  }

  //Toggles the sidebar being shown
  toggleSidebar(): void {
    this.sidebarService.toggleSideBar();
  }

  //Toggles the sidebar burger icon from being shown
  toggleBurgerIcon(): void {
    this.sidebarService.toggleBurgerIcon();
  }

  //Appends the selected char to the seller service login value
  loginAppend(char: string): void {
    this.sellerService.appendLogin(char);
  }

  //Removes the last character within the login value
  loginBackspace(): void {
    this.sellerService.backspaceLogin();
  }

  //Tells the seller service to login
  loginSubmit(): void {
    this.sellerService.submit();
  }

  //Makes a payment within the reciept service
  makePayment(value?: any): void {
    this.receiptService.payment(value);
  }

  //Clears the transactions
  clearTransaction(): void {
    this.receiptService.clearTransaction();
  }

  //Toggle info mode, this allows the user to find out more info about products
  toggleInfo(): void {
    this.infoMode = !this.infoMode;
  }

  //Opens a modal that shows previous transactions
  viewTransactions(): void {
    this.modalService.open(TransactionViewComponent, { size: "lg" });
  }

}
