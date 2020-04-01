import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ReceiptService } from '../receipt.service';

@Component({
  selector: 'app-receipt-view',
  templateUrl: './receipt-view.component.html',
  styleUrls: ['./receipt-view.component.css']
})
export class ReceiptViewComponent implements OnInit {

  constructor(public receiptService: ReceiptService,
    private detector: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.receiptService.updateEvent.subscribe(() => {
      this.receiptViewUpdate();
  });
  }

  receiptViewUpdate() {
    this.detector.detectChanges();
    let div = document.getElementById('tableContainer');
    if (div) {
      div.scrollTop = div.scrollHeight;
    }
  }

  remove(index: any) {
    this.receiptService.removeItem(index);
  }


}
