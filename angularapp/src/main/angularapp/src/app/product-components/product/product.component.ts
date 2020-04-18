import { Component, OnInit, Input } from '@angular/core';
import { ToasterService, ToastType } from 'src/app/toaster/toaster.service';
import { ProductAPIService, Product, Group } from 'src/app/openapi';
import { ActivatedRoute, Router } from '@angular/router';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  //Local reference for ngmodel
  product: Product = {};

  //The list of groups to be displayed within the component
  groups: Group[] = [];
  //The selected group id
  selectedGroupId: number = 1;

  //Boolean flags to represent component mode
  isEdit: boolean = false;
  infoMode: boolean = false;

  /**
   * @param toaster to raise toasts within the application
   * @param productService  service to create and retrieve products
   * @param router to navigate the user to other components
   * @param modal service used to show modals
   */
  constructor(private toaster: ToasterService,
    private productService: ProductAPIService,
    private router: Router,
    public modal: NgbModal) {
    }

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
  ressurect(id: string): void {
    this.productService.getProduct(+id).subscribe(result => {
      this.product = result;
      this.isEdit = true;
      this.selectedGroupId = this.product.group.id;
    })
  }

  //Finds a group from within the list
  findGroup(): Group {
    return this.groups.find(g => g.id == this.selectedGroupId);
  }

  //Navigate back to the list
  cancel(): void {
    this.router.navigateByUrl("/define/product/list")
  }

  //Create the product
  createProduct(): void {
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
  saveChanges(): void {
    this.product.group = this.findGroup();
    this.productService.updateProduct(this.product).subscribe(res => {
      this.router.navigateByUrl("/define/product/list");
    }, err => {
      this.toaster.createToast(err, ToastType.DANGER);
    })
  }

}
