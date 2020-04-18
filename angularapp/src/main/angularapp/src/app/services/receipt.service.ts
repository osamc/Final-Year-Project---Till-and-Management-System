import { Injectable, ApplicationRef, ChangeDetectorRef, NgZone } from '@angular/core';
import { Product, TransactionRecord, SellerAPIService, TransactionAPIService, Transaction } from '../openapi';
import { SellerService } from './seller.service';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReceiptService {

  //Create an object that is subscribable, this allows other services 
  //to listen for changes
  public updateEvent: BehaviorSubject<null> = new BehaviorSubject<null>(null);

  //Keep track of each seller and their transaction list,
  transactionAssociations = {};
  runningTotal = 0;

  //the active transactions to display within the component
  activeTransactions: TransactionRecord[] = [];

  //Quantity of items to add/remove
  quantity: string = "";

  /**
   * @param sellerService used to tie the transaction to the seller
   * @param transactionService  used to create the transaction
   */
  constructor(private sellerService: SellerService,
    private transactionService: TransactionAPIService) {
    //subscribe to any seller change events
    //we will use this to swap the transactions shown on screen
    this.sellerService.activeSeller.subscribe(newSeller => {
      if (newSeller != null) {
        this.changeSeller();
      }
    })
  }

  //Updates the seller now using the app, saves the previous
  //sellers transactions in an array so they can be retrieved once they log 
  //back in
  changeSeller(): void {
    let previous = this.sellerService.previousSeller;
    let active = this.sellerService.activeSeller.value;

    //If we have a previous seller, save their transactions
    if (previous) {
      this.transactionAssociations[previous.id] = this.activeTransactions;
    }

    //get the new set of transactions to display/a new empty transaction list
    this.activeTransactions = this.transactionAssociations[active.id] ? this.transactionAssociations[active.id] : [];
    this.runningTotal = 0;

    this.activeTransactions.forEach(transaction => {
      this.runningTotal += transaction.price;
    })

  }

  //Add an item to the transaction, if the previously added item is the same, 
  //the quantity will update, this will make it more readable for the user.
  //This would mean they could just see the quantity instead of having to count 
  //x rows
  addItem(product: Product): void {
    let lastItem = this.activeTransactions.slice(-1)[0];
    let quantity = this.getQuantity();
    let price = (product.price * quantity);

    if (lastItem && lastItem.product.id === product.id) {
      lastItem.quantity += quantity;
      lastItem.price += price;
    } else {
      this.activeTransactions.push({ quantity: quantity, product: product, price: price });
    }

    this.runningTotal += price;
    this.updateEvent.next(null);

  }

  getQuantity(): number {
    let toReturn = 1;

    if (this.quantity.includes("X")) {
      toReturn = +(this.quantity.replace('X', ''));
      this.quantity = "";
    }
    return toReturn;
  }

  //removes an item from transactions
  removeItem(index: any): void {
    let item = this.activeTransactions[+index];
    let quantity = this.getQuantity();
    let price = (item.product.price * quantity);

    if (item.quantity - quantity <= 0) {
      this.runningTotal -= item.price;
      this.activeTransactions.splice(index, 1);
    } else {
      item.quantity -= quantity;
      item.price -= price;
      this.runningTotal -= price;
    }
  }

  //Clears the transaction
  clearTransaction(): void {
    this.activeTransactions = [];
    this.runningTotal = 0;
  }

  //Append a char to the quantity
  quantityAppend(char: any): void {
    if (char === "X") {
      if (this.quantity.length == 0) {
        return;
      }
    }

    if (this.quantity.includes('X')) {
      this.quantity = "";
    }

    this.quantity += char;

    this.transactionService.getSellerTransactions(this.sellerService.activeSeller.value.id).subscribe(res => {
      console.log(res);
    })

  }

  //Take payment and create the transaction
  payment(amount?: any): void {
    amount = amount ? amount : (this.quantity.includes('X') ? 0 : this.quantity);
    //convert pence into pounds
    amount = amount / 100;

    this.runningTotal -= amount;

    if (this.activeTransactions.length == 0 && this.runningTotal < 0) {
      this.runningTotal = 0;
    }

    this.quantity = "";

    if (this.runningTotal < 0 && this.activeTransactions.length > 0) {
      let transaction: Transaction = {};
      transaction.transactions = this.activeTransactions;
      transaction.sellerId = this.sellerService.activeSeller.value.id;
      this.transactionService.createTransaction(transaction).subscribe(res => {
        this.activeTransactions = [];
      });
    }

  }



}
