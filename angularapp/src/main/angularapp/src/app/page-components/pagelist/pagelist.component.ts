import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PageAPIService, PageInfo } from 'src/app/openapi';

@Component({
  selector: 'app-pagelist',
  templateUrl: './pagelist.component.html',
  styleUrls: ['./pagelist.component.css']
})
export class PagelistComponent implements OnInit {

  //The list of pages to be shown within the application
  pages: PageInfo[] = [];

  /**
   * 
   * @param router used to navigate between pages
   * @param pageService the service used to retieve the list of pages
   */
  constructor(private router: Router,
    private pageService: PageAPIService) { }

  //When the page has loaded, we want to grab the list
  //of pages
  ngOnInit() {
   this.loadList();
  }

  //Load the list of pages and assign them to the variable used in the template
  loadList(): void {
    this.pageService.getPages().subscribe(res => {
      this.pages = res;
    });
  }

  //Takes a given page and deletes it
  deletePage(page: PageInfo): void {
    this.pageService.deletePage(page.infoId).subscribe(res => {
      //if the page has been deleted, we want to reload the list
      if (res) {
        this.loadList();
      }
    });
  }

  //Takes a given id and navigates the user to the edit component
  editPage(id: any): void {
    this.router.navigateByUrl("/define/page/edit/" + id);
  }

  //Navigates the user to the create page component
  createPage(): void {
    this.router.navigateByUrl("/define/page/create")
  }

}
