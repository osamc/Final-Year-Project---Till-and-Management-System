import { Component, OnInit } from '@angular/core';
import { Transaction, TransactionAPIService } from '../openapi';

@Component({
  selector: 'app-transaction-view',
  templateUrl: './transaction-view.component.html',
  styleUrls: ['./transaction-view.component.css']
})
export class TransactionViewComponent implements OnInit {


  activeTransaction: Transaction = {};
  id: number = 0;

  constructor(private transactionService: TransactionAPIService) {
  }

  ngOnInit(): void {
    this.transactionService.getNewest().subscribe(res => {
      this.setTransaction(res);
    })
  }


  changeActive(incrament: number) {
    this.id += incrament;


      this.transactionService.getTransaction(this.id).subscribe(res => {
        this.setTransaction(res);
      }, err => {
        this.id -= incrament;
        console.log(this.id);
      });
   


  }

  setTransaction(trans: any) {
    this.activeTransaction = trans;
    this.id = this.activeTransaction.transactionId;
  }



}
