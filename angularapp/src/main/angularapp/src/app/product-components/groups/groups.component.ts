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

  group: Group = {};
  isEdit: boolean = false;

  constructor(private toaster: ToasterService,
    private productService: ProductAPIService,
    private router: Router) { }

  ngOnInit(): void {
    if (this.router.url.includes("define/group/edit")) {
      this.ressurect(this.router.url.split("/").slice(-1)[0] );
    }
  }

  ressurect(id: any) {
    console.log('edit' + id);
    this.productService.getGroup(+id, false).subscribe(res => {
      this.group = res;
      this.isEdit = true;
    })
  }

  cancel() {
    this.router.navigateByUrl("/define/product/list");
  }

  saveChanges() {
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

  createGroup() {
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
