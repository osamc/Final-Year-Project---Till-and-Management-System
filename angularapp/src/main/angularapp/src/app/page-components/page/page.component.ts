import { Component, OnInit } from '@angular/core';
import { PageInfo } from 'src/app/openapi';

@Component({
  selector: 'app-page',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.css']
})
export class PageComponent implements OnInit {

  page: PageInfo = {xrows: 1, yrows: 1};

  productArray: any[] = [[]];

  constructor() { }

  ngOnInit() {
    this.changeRows(1, 1)
  }

  changeRows(x: any, y: any) {
    

    this.page.xrows = x != null ? x : 1;
    this.page.yrows = y != null ? y : 1;
    console.log("x: " + x + " y: " + y);
    console.log(this.page);

    
    let columns = [];

    for(let j = 0; j < y; j++) {
      let row = [];
      for(let i = 0; i < x; i++) {
        row.push({name: "x:" + i + ", y:" + j});
      }

      columns.push(row);
    }
  
    this.productArray = columns;
    console.log(this.productArray);

  }

}
