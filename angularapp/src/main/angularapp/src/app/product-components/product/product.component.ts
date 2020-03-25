import { Component, OnInit } from '@angular/core';
import { ToasterService, ToastType } from 'src/app/toaster/toaster.service';
import { ProductAPIService, Product, Group } from 'src/app/openapi';
import { ActivatedRoute, Router } from '@angular/router';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  product: Product = {};

  groups: Group[] = [];
  selectedGroupId: number = 1;

  isEdit: boolean = false;

  constructor(private toaster: ToasterService,
    private productService: ProductAPIService,
    private router: Router) {}

  ngOnInit() {
    this.productService.getGroups(false).subscribe(res => {
      this.groups = res;
    });
    //if we are on the edit route then we want to grab the details of the product
    if (this.router.url.includes("define/product/edit")) {
      this.ressurect(this.router.url.split("/").slice(-1)[0] );
    }
  }

  //Ressurect a product to edit
  ressurect(id: string) {
    this.productService.getProduct(+id).subscribe(result => {
      this.product = result;
      this.isEdit = true;
      this.selectedGroupId = this.product.group.id;
    })
  }

  findGroup() {
    return this.groups.find(g => g.id == this.selectedGroupId);
  }

  //Navigate back to the list
  cancel() {
    this.router.navigateByUrl("/define/product/list")
  }

  //Create the product
  createProduct() {
    this.product.group = this.findGroup();
    this.productService.createProduct(this.product).subscribe(res => {
      if (res) {
        this.router.navigateByUrl("/define/product/list");
      } else {
        this.toaster.createToast("There was an issue creating your product", ToastType.DANGER);
      }
    }, err => {
      this.toaster.createToast(err, ToastType.DANGER);
    });
  }

  //Save changes to an existing product
  saveChanges() {
    this.product.group = this.findGroup();
    this.productService.updateProduct(this.product).subscribe(res => {
      this.router.navigateByUrl("/define/product/list");
    }, err => {
      this.toaster.createToast(err, ToastType.DANGER);
    })
  }

}
