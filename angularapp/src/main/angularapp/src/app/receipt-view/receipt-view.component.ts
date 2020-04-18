import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ReceiptService } from '../services/receipt.service';

@Component({
  selector: 'app-receipt-view',
  templateUrl: './receipt-view.component.html',
  styleUrls: ['./receipt-view.component.css']
})
export class ReceiptViewComponent implements OnInit {

  /**
   * @param receiptService used to be able to get the list of items on the reciept
   * @param detector used to force updates within the application
   */
  constructor(public receiptService: ReceiptService,
    private detector: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.receiptService.updateEvent.subscribe(() => {
      this.receiptViewUpdate();
  });
  }

  //We want to force updates to occur, then we want to scroll to the very bottom
  //to ensure the last item added is shown
  receiptViewUpdate(): void {
    this.detector.detectChanges();
    let div = document.getElementById('tableContainer');
    if (div) {
      div.scrollTop = div.scrollHeight;
    }
  }
  
  //Removes the product from the list
  remove(index: any): void {
    this.receiptService.removeItem(index);
  }


}
