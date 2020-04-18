import { Pipe, PipeTransform } from '@angular/core';
import { Transaction } from '../openapi';

@Pipe({
  name: 'totalPrice'
})
export class TotalPricePipe implements PipeTransform {

  //Takes a transaction and sums up all items and returns their total cost
  transform(transaction: Transaction): number {
    let totalPrice = 0;
    if (transaction.transactions) {
      transaction.transactions.forEach(transaction => {
        totalPrice += transaction.price;
      })
    }
    return totalPrice;
  }

}
