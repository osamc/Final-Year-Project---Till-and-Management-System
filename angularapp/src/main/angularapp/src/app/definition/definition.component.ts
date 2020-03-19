import { Component, OnInit } from '@angular/core';
import { PageAPIService } from '../openapi';
import { Router } from '@angular/router';

@Component({
  selector: 'app-definition',
  templateUrl: './definition.component.html',
  styleUrls: ['./definition.component.css']
})
export class DefinitionComponent implements OnInit {

  constructor(private pageService: PageAPIService,
    private router: Router) { }

  ngOnInit() {
  }

}
