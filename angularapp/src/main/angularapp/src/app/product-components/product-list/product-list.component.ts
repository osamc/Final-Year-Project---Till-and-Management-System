import { Component, OnInit } from '@angular/core';
import { ProductAPIService, Product, Group } from 'src/app/openapi';
import { VirtualTimeScheduler } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];
  groups: Group[] = [];

  constructor(private productAPI: ProductAPIService,
    private router: Router) { }

  ngOnInit() {
    this.productAPI.getProducts().subscribe(res => {
      console.log(res);
      this.products = res;
    });

    this.productAPI.getGroups(true).subscribe(res => {
      console.log(res);
      this.groups = res;
    });
  }

  createProduct() {
    this.router.navigateByUrl("define/product/create");
  }

  editProduct(id: any) {
    this.router.navigateByUrl("define/product/edit/" + id);
  }

}
