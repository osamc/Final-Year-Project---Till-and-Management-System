import { Component, OnInit } from '@angular/core';
import { Group } from 'src/app/openapi/model/models';
import { ToasterService, ToastType } from 'src/app/toaster/toaster.service';
import { ProductAPIService } from 'src/app/openapi';
import { Router } from '@angular/router';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.css']
})
export class GroupsComponent implements OnInit {

  //Local variable of the group for ngModel binding
  group: Group = {};
  //Boolean flag representing if we are in edit mode
  isEdit: boolean = false;

  /**
   * @param toaster used to raise toasts
   * @param productService  used to create new groups
   * @param router used to naviage the user to alternative components
   */
  constructor(private toaster: ToasterService,
    private productService: ProductAPIService,
    private router: Router) { }

  //When we initialise the component we want to
  //check if we are in edit mode and if we are
  //we then want to ressurect the group we are editing
  ngOnInit(): void {
    if (this.router.url.includes("define/group/edit")) {
      this.ressurect(this.router.url.split("/").slice(-1)[0]);
    }
  }

  //Takes a given id and retrieves that group from the database
  ressurect(id: any): void {
    console.log('edit' + id);
    this.productService.getGroup(+id, false).subscribe(res => {
      this.group = res;
      this.isEdit = true;
    })
  }

  //Navigates the user to the list page
  cancel(): void {
    this.router.navigateByUrl("/define/product/list");
  }

  //Saves the group
  saveChanges(): void {
    this.productService.updateGroup(this.group).subscribe(res => {
      if (res) {
        this.router.navigateByUrl('/define/product/list')
      } else {
        this.toaster.createToast("There was an issue updating your group", ToastType.DANGER);
      }
    }, err => {
      this.toaster.createToast(err, ToastType.DANGER);
    });
  }

  //Creates a new group
  createGroup(): void {
    this.productService.createGroup(this.group.name).subscribe(res => {
      if (res) {
        this.router.navigateByUrl('/define/product/list')
      } else {
        this.toaster.createToast("There was an issue creating your group", ToastType.DANGER);
      }
    }, err => {
      this.toaster.createToast(err, ToastType.DANGER);
    });
  }

}
