import { Component, OnInit } from '@angular/core';
import { ProductAPIService, Product, Group } from 'src/app/openapi';
import { VirtualTimeScheduler } from 'rxjs';
import { Router } from '@angular/router';
import { ToasterService, ToastType } from 'src/app/toaster/toaster.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];
  groups: Group[] = [];

  constructor(private productAPI: ProductAPIService,
    private router: Router,
    private toaster: ToasterService) { }

  ngOnInit() {
   this.updateView();
  }

  createProduct() {
    this.router.navigateByUrl("define/product/create");
  }

  createGroup() {
    this.router.navigateByUrl("define/group/create");
  }

  editProduct(id: any) {
    this.router.navigateByUrl("define/product/edit/" + id);
  }

  editGroup(id: any) {
    this.router.navigateByUrl("define/group/edit/" + id);
  }

  updateView() {
    this.productAPI.getProducts().subscribe(res => {
      this.products = res;
    });

    this.productAPI.getGroups(true).subscribe(res => {
      this.groups = res;
    });
  }

  deleteProduct(product: Product) {
    this.productAPI.deleteProduct(product).subscribe(res => {
      this.toaster.createToast("Product: " + product.name + " was deleted successfully", ToastType.INFO);
      this.updateView();
    })
  }

  deleteGroup(group: Group) {
    this.productAPI.deleteGroup(group).subscribe(res => {
      this.toaster.createToast("Group: " + group.name + " was deleted successfully", ToastType.INFO);
      this.updateView();
    })
  }

}
