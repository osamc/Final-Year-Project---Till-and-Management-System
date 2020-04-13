import { Component, OnInit } from '@angular/core';
import { ReceiptService } from '../receipt.service';

@Component({
  selector: 'app-quantity',
  templateUrl: './quantity.component.html',
  styleUrls: ['./quantity.component.css']
})
export class QuantityComponent implements OnInit {

  quantityButtons: string[] = [];

  constructor(private receiptService: ReceiptService) { }

  ngOnInit(): void {
    for(let i = 1; i < 10; i++) {
      this.quantityButtons.push(String(i));
    }

    this.quantityButtons.push("0");
    this.quantityButtons.push("00");
    this.quantityButtons.push("X");

  }

  addChar(i: any) {
    this.receiptService.quantityAppend(i);
  }

}
