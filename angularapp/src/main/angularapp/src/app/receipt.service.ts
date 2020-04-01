import { Injectable, ApplicationRef, ChangeDetectorRef, NgZone } from '@angular/core';
import { Product, TransactionRecord, SellerAPIService } from './openapi';
import { SellerService } from './seller.service';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReceiptService {

  public updateEvent: BehaviorSubject<null> = new BehaviorSubject<null>(null);

  //Keep track of each seller and their transaction list,
  transactionAssociations = {};
  runningTotal = 0;

  //the active transactions to display within the component
  activeTransactions: TransactionRecord[] = [];

  constructor(private sellerService: SellerService) {
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
  changeSeller() {
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
  addItem(product: Product) {
    let lastItem = this.activeTransactions.slice(-1)[0];

    if (lastItem && lastItem.product.id === product.id) {
      lastItem.quantity++;
      lastItem.price += product.price;
    } else {
      this.activeTransactions.push({ quantity: 1, product: product, price: product.price });
    }

    this.runningTotal += product.price;
    this.updateEvent.next(null);

  }

  //removes an item from transactions
  removeItem(index: any) {
    let item = this.activeTransactions[+index];
    item.quantity -= 1;
    item.price -= item.product.price;
    if (item.quantity == 0) {
      this.activeTransactions.splice(index, 1);
    }
    this.runningTotal -= item.product.price;
  }

  //Clears the transaction
  clearTransaction() {
    this.activeTransactions = [];
  }

}
