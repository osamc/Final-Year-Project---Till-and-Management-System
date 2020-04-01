import { Component, OnInit, Input } from '@angular/core';
import { Product } from '../openapi';
import { ReceiptService } from '../receipt.service';

@Component({
  selector: 'app-tillbutton',
  templateUrl: './tillbutton.component.html',
  styleUrls: ['./tillbutton.component.css']
})
export class TillbuttonComponent {

  //The product associated with the button
  @Input()
  product: Product = {};

  //Boolean indicating what should happen when it is pressed
  @Input()
  creationMode: boolean = false;

  constructor(private receiptService: ReceiptService) { }

  addItem() {
    if (!this.creationMode) {
      this.receiptService.addItem(this.product);
    }
  }
 
}
