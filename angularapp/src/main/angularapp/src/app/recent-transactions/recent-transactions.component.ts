import { Component, OnInit } from '@angular/core';
import { TransactionAPIService, Transaction } from '../openapi';
import { ToasterService, ToastType } from '../toaster/toaster.service';
import { WebsocketService } from '../services/websocket.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TransactionViewComponent } from '../transaction-view/transaction-view.component';

@Component({
  selector: 'app-recent-transactions',
  templateUrl: './recent-transactions.component.html',
  styleUrls: ['./recent-transactions.component.css']
})
export class RecentTransactionsComponent implements OnInit {

  /**
   * 
   * @param transactionService  Used to retrieve transactions
   * @param toaster  Used to create toast notificaitons
   * @param websocket Used to update in real time
   * @param modal Used to open modals
   */
  constructor(private transactionService: TransactionAPIService,
    private toaster: ToasterService,
    private websocket: WebsocketService,
    private modal: NgbModal) {
      this.websocket.ws.subscribe(res => {
        //If we're at the start of the list, we want to update it so they can see new transactions
        //otherwise it will constantly reset and viewing would be quite hard
        if (this.pageOffset == 0) {
          this.updateTable(false);
        }
      })
    }

  pageSize: number = 10;
  pageOffset: number = 0;

  transactions: Transaction[] = [];

  ngOnInit(): void {
   this.updateTable(false);
  }

  //Updates the table view
  updateTable(manual: boolean): void {

    if (this.pageSize < 0) {
      this.pageSize = 1;
    }

    this.transactionService.getTransactionPage(this.pageSize, this.pageOffset).subscribe(res => {
      if (res.length != 0) {
        this.transactions = res;
      } else {
        this.pageOffset -= 1;
      }
     
      if (manual) {
        this.toaster.createToast("Transaction List updated", ToastType.SUCCESS);
      }

    });
  }

  //Changes the pages offset
  changePage(offset): void {
    this.pageOffset += offset;

    this.pageOffset = this.pageOffset < 0 ? 0 : this.pageOffset;

    this.updateTable(false);
  }

  //Opens the Transaction View Modal allowing for the given transaction
  //to be viewed
  openTransaction(transaction): void {
    let ref = this.modal.open(TransactionViewComponent);
    ref.componentInstance.activeTransaction = transaction;
  }

}
