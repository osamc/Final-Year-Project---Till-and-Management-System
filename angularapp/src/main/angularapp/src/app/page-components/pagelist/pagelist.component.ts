import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PageAPIService, PageInfo } from 'src/app/openapi';

@Component({
  selector: 'app-pagelist',
  templateUrl: './pagelist.component.html',
  styleUrls: ['./pagelist.component.css']
})
export class PagelistComponent implements OnInit {

  pages: PageInfo[] = [];

  constructor(private router: Router,
    private pageService: PageAPIService) { }

  ngOnInit() {
   this.loadList();
  }

  loadList() {
    this.pageService.getPages().subscribe(res => {
      this.pages = res;
    });
  }

  deletePage(page: PageInfo) {
    this.pageService.deletePage(page.infoId).subscribe(res => {
      if (res) {
        this.loadList();
      }
    });
  }

  editPage(id: any) {
    this.router.navigateByUrl("/define/page/edit/" + id);
  }

  createPage() {
    this.router.navigateByUrl("/define/page/create")
  }

}
