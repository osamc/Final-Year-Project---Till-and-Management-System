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
    this.pageService.getPages().subscribe(res => {
      this.pages = res;
    });
  }

  deletePage(page: PageInfo) {

  }

  editPage(id: any) {

  }

  createPage() {
    this.router.navigateByUrl("/define/page/create")
  }

}
