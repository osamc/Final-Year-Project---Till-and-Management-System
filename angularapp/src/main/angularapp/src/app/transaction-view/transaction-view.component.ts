import { Component, OnInit } from '@angular/core';
import { Transaction, TransactionAPIService } from '../openapi';

@Component({
  selector: 'app-transaction-view',
  templateUrl: './transaction-view.component.html',
  styleUrls: ['./transaction-view.component.css']
})
export class TransactionViewComponent implements OnInit {

  //local variable for ngmodel to be displayed
  activeTransaction: Transaction = {};
  id: number = 0;

  /**
   * @param transactionService  used to retrieve the transactions
   */
  constructor(private transactionService: TransactionAPIService) {
  }

  ngOnInit(): void {
    //If this is not undefined, then it has been provided through a modal
    if (this.activeTransaction.transactionId == undefined) {
      this.transactionService.getNewest().subscribe(res => {
        this.setTransaction(res);
      })
    }
    
  }

  //Changes the active transaction
  changeActive(incrament: number): void {
    this.id += incrament;
    this.transactionService.getTransaction(this.id).subscribe(res => {
      this.setTransaction(res);
    }, err => {
      this.id -= incrament;
      console.log(this.id);
    });
  }

  //Sets the current transaction
  setTransaction(trans: any): void {
    this.activeTransaction = trans;
    this.id = this.activeTransaction.transactionId;
  }



}
