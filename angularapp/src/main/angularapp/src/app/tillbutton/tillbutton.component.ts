import { Component, OnInit, Input } from '@angular/core';
import { Product } from '../openapi';
import { ReceiptService } from '../services/receipt.service';
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
  isCreation: boolean = false;

  //Boolean to represent if we should show the info modal
  @Input()
  isInfo: boolean = false;

  //Boolean used to represent if the buttons should be fully disabled
  @Input()
  isDisabled: boolean = true;

  /**
   * @param receiptService used to add products
   * @param modal used to show the info modal
   */
  constructor(private receiptService: ReceiptService,
    private modal: NgbModal) {}

  //Adds an item to the reciept
  addItem(): void {
    if (this.isInfo){
      let modal = this.modal.open(ProductComponent, {size: "lg"});
      modal.componentInstance.infoMode = true;
      modal.componentInstance.product = this.product;
    } else {
      if (!this.isCreation && !this.isDisabled) {
        this.receiptService.addItem(this.product);
      }
    }
  }
 
}
