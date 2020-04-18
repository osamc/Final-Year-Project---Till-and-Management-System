import { Component, OnInit } from '@angular/core';
import { ReceiptService } from '../services/receipt.service';
import { SellerService } from '../services/seller.service';

@Component({
  selector: 'app-quantity',
  templateUrl: './quantity.component.html',
  styleUrls: ['./quantity.component.css']
})
export class QuantityComponent implements OnInit {

  //Buttons to be displayed
  quantityButtons: string[] = [];

  /**
   * @param receiptService used for setting the quantity values within the service
   * @param sellerService used to check if the component should be disabled
   */
  constructor(private receiptService: ReceiptService,
    public sellerService: SellerService) { }

  ngOnInit(): void {
    //We want all the button values so [1,2,3,4,5,6,7,8,9,0,00,x] in that order
    //so we start at 1 instead of 0 for the desired order
    for(let i = 1; i < 10; i++) {
      this.quantityButtons.push(String(i));
    }

    //We then manually push the values the for loop can not create
    this.quantityButtons.push("0");
    this.quantityButtons.push("00");
    this.quantityButtons.push("X");

  }

  //Adds a char to the service
  addChar(i: any): void {
    this.receiptService.quantityAppend(i);
  }

}
