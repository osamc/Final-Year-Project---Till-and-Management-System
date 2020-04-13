import { Component, OnInit, Input } from '@angular/core';
import { Product } from '../openapi';
import { ReceiptService } from '../receipt.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ProductComponent } from '../product-components/product/product.component';

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

  @Input()
  infoMode: boolean = false;

  constructor(private receiptService: ReceiptService,
    private modal: NgbModal) { }

  addItem() {
    if (this.infoMode){
      let modal = this.modal.open(ProductComponent, {size: "lg"});
      modal.componentInstance.infoMode = true;
      modal.componentInstance.product = this.product;
    } else {
      if (!this.creationMode) {
        this.receiptService.addItem(this.product);
      }
    }
  }
 
}
