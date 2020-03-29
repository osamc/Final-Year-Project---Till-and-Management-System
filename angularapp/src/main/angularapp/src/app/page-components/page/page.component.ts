import { Component, OnInit } from '@angular/core';
import { PageInfo, ProductAPIService, Group, Product, PageAPIService } from 'src/app/openapi';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { forkJoin } from 'rxjs';
import { Router } from '@angular/router';
import { ToasterService, ToastType } from 'src/app/toaster/toaster.service';

@Component({
  selector: 'app-page',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.css']
})
export class PageComponent implements OnInit {

  isEdit: boolean = false;

  //list of groups to be shown within modal
  groups: Group[] = [];

  //selected options for association
  selectedGroup: Group = {};
  selectedProduct: Product = {};

  //the page definition
  page: PageInfo = { xrows: 2, yrows: 2, pageDefinitions: [] };

  productArray: any[] = [[]];

  constructor(private modalService: NgbModal,
    private productService: ProductAPIService,
    private pageService: PageAPIService,
    private router: Router,
    private toaster: ToasterService) { }

  ngOnInit() {
    this.productService.getGroups(true).subscribe(res => {
      this.groups = res;
      this.selectedGroup = this.groups[0];
    });
    this.changeRows(2, 2)
  }

  openProductAssociationModal(modal: any, x: any, y: any) {
    let modalRef = this.modalService.open(modal);
    modalRef.result.then(res => {
      if (res === 'save') {
        this.productArray[y][x] = this.selectedProduct;
      }
    }, reject => { });
  }

  //When a group is selected, we want to make sure the first option in the products is selected
  groupSelected() {
    this.selectedProduct = this.selectedGroup.products.length > 0 ? this.selectedGroup.products[0] : {};
  }

  removeItem(x: any, y: any) {
    if (this.isEdit) {
      this.pageService.removeItemFromPage(x, y).subscribe(res => {
        this.toaster.createToast("Item removed from page", ToastType.SUCCESS);
      }, err => {
        this.toaster.createToast(err, ToastType.DANGER);
      });
    }
    this.productArray[y][x] = null;
  }

  changeRows(x: any, y: any) {
    this.page.xrows = x != null ? x : 1;
    this.page.yrows = y != null ? y : 1;

    this.productArray.length = y;

    for (let i = 0; i < this.productArray.length; i++) {
      if (this.productArray[i] === null || this.productArray[i] === undefined) {
        this.productArray[i] = [];
      }
      this.productArray[i].length = x;
    }

    for(let i = 0; i < this.productArray.length; i++) {
      for (let j = 0; j < this.productArray[i].length; j++) {
        if (!this.productArray[i][j]) {
          this.productArray[i][j] = null;
        }
      }
    }
  }

  savePage() {
    this.pageService.createPage(this.page).subscribe(res => {

      let assocationObservables = [];

      for(let i = 0; i < this.productArray.length; i++) {
        for(let j = 0; j < this.productArray[i].length; j++) {
          if (this.productArray[i][j] !== null) {
              assocationObservables.push(this.pageService.addItemToPage({page: res, x: j, y: i, product: this.productArray[i][j]}));
          }
        }
      }

      let assocationObservable = forkJoin(assocationObservables);

      assocationObservable.subscribe(res => {
        this.router.navigateByUrl('define/page/list');
      }, err => {
        this.toaster.createToast(err, ToastType.DANGER);
      });

    });
  }

  cancel() {
    this.router.navigateByUrl('define/page/list');
  }



}
