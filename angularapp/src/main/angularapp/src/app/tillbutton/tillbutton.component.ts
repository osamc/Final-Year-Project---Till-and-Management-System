import { Component, OnInit, Input } from '@angular/core';
import { Product } from '../openapi';

@Component({
  selector: 'app-tillbutton',
  templateUrl: './tillbutton.component.html',
  styleUrls: ['./tillbutton.component.css']
})
export class TillbuttonComponent implements OnInit {

  @Input()
  product: Product = {};

  @Input()
  creationMode: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  test() {
    console.log(this.product.name);
  }
 
}
