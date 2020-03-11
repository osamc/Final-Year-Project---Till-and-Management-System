import { Component, OnInit } from '@angular/core';
import { PageAPIService } from '../openapi';

@Component({
  selector: 'app-definition',
  templateUrl: './definition.component.html',
  styleUrls: ['./definition.component.css']
})
export class DefinitionComponent implements OnInit {

  constructor(private pageService: PageAPIService) { }

  ngOnInit() {
    this.pageService.getPages().subscribe(res => {
      console.log(res);
    })
  }

}
