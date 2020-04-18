import { Component, OnInit } from '@angular/core';
import { PageInfo, ProductAPIService, Group, Product, PageAPIService } from 'src/app/openapi';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { forkJoin } from 'rxjs';
import { Router } from '@angular/router';
import { ToasterService, ToastType } from 'src/app/toaster/toaster.service';
import { isObject } from 'util';
import { SellerService } from 'src/app/services/seller.service';

@Component({
  selector: 'app-page',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.css']
})
export class PageComponent implements OnInit {

  //a boolean indicating if we are in edit mode
  isEdit: boolean = false;

  //list of groups to be shown within modal
  groups: Group[] = [];

  //selected options for association
  selectedGroup: Group = {};
  selectedProduct: Product = {};

  //the page definition
  page: PageInfo = { xrows: 2, yrows: 2, productAssociations: [] };

  //The list of products to be shown
  productArray: any[] = [[]];

  /**
   * @param modalService Modal Service to allow for products to be selected
   * @param productService Used to select and find products
   * @param pageService Used to create pages
   * @param router Used to navigate between pages
   * @param toaster Used to raise toasts within the application
   */
  constructor(private modalService: NgbModal,
    private productService: ProductAPIService,
    private pageService: PageAPIService,
    private router: Router,
    private toaster: ToasterService) { }


  ngOnInit() {
    //Grab a list of all groups so the user is able to 
    //find the products they want easier
    this.productService.getGroups(true).subscribe(res => {
      this.groups = res;
      this.selectedGroup = this.groups[0];
    });

    //If we have this url, we have a page to load
    if (this.router.url.includes("define/page/edit")) {
      this.ressurect(this.router.url.split("/").slice(-1)[0]);
    } else {
      //else we're creating a new page
      this.changeRows(2, 2)
    }

  }

  //Takes a given id and retrieves the entry from the database
  //allowing for us to edit the item
  ressurect(id: any): void {
    this.pageService.getPage(id).subscribe(res => {
      this.isEdit = true;
      this.page = res;
      this.changeRows(this.page.xrows, this.page.yrows);
      this.page.productAssociations.forEach(association => {
        this.productArray[association.y][association.x] = association.product;
      })

    })
  }

  //Opnes the product association modal and then takes the selected
  //item and adds it to the page at the specified location
  openProductAssociationModal(modal: any, x: any, y: any): void {
    let modalRef = this.modalService.open(modal);
    modalRef.result.then(res => {
      if (res === 'save') {
        this.productArray[y][x] = this.selectedProduct;
      }
    }, reject => { });
  }

  //When a group is selected, we want to make sure the first option in the
  // products is selected
  groupSelected(): void {
    this.selectedProduct = this.selectedGroup.products.length > 0 ? this.selectedGroup.products[0] : {};
  }

  //Removes an item from the product list
  removeItem(x: any, y: any): void {
    this.productArray[y][x] = null;
  }

  //Manipulates the number of rows to be shown
  changeRows(x: any, y: any): void {
    //ensure that the minimum number of items is always 1
    this.page.xrows = (x != null ? x : 1);
    this.page.yrows = (y != null ? y : 1);
    //due to how arrays work in js/ts, we are able to 
    //manipulate their length directly
    this.productArray.length = y;

    //We then iterate through the arrays and ensure that all rows contain atleast empty arrays.
    // We also ensure each row has the right number of x values
    for (let i = 0; i < this.productArray.length; i++) {
      if (this.productArray[i] === null || this.productArray[i] === undefined) {
        this.productArray[i] = [];
      }
      this.productArray[i].length = x;
    }

    //We then iterate over the arrays to ensure that there are no
    //undefined values
    for (let i = 0; i < this.productArray.length; i++) {
      for (let j = 0; j < this.productArray[i].length; j++) {
        if (!this.productArray[i][j]) {
          this.productArray[i][j] = null;
        }
      }
    }
  }

  //Converts the 2d array we have into product associations to be used
  //by the server
  createDefs(): void {
    this.page.productAssociations = [];
    for (let i = 0; i < this.productArray.length; i++) {
      for (let j = 0; j < this.productArray[i].length; j++) {
        if (this.productArray[i][j] != null) {
          this.page.productAssociations.push({ product: this.productArray[i][j], x: j, y: i })
        }
      }
    }
  }

  //Saves the edited page
  savePage(): void {
    this.createDefs();
    this.pageService.updatePage(this.page).subscribe(res => {
      if (res) {
        this.router.navigateByUrl('define/page/list');
      }
    });
  }

  //Creates a new page
  createPage(): void {
    this.createDefs();
    this.pageService.createPage(this.page).subscribe(res => {
      if (res) {
        this.router.navigateByUrl('define/page/list');
      }
    });
  }

  //Returns the user back to the page list component
  cancel(): void {
    this.router.navigateByUrl('define/page/list');
  }



}
