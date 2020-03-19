import { Component, OnInit } from '@angular/core';
import { ToasterService, ToastType } from 'src/app/toaster/toaster.service';
import { ProductAPIService, Product } from 'src/app/openapi';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  product: Product = {};
  isEdit: boolean = false;

  constructor(private toaster: ToasterService,
    private productService: ProductAPIService,
    private route: ActivatedRoute,
    private router: Router) {
      console.log(route);
   }

  ngOnInit() {
  }

  cancel() {
    this.router.navigateByUrl("/define/product/list")
  }

  createProduct() {
    console.log(this.product);
  }

}
