import { Component, OnInit, ChangeDetectorRef, OnDestroy } from '@angular/core';

import {Chart, ChartOptions} from 'chart.js'
import { ChartAPIService } from '../openapi/api/chartAPI.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  constructor(private chartService: ChartAPIService,
    private change: ChangeDetectorRef) { }

  options: any[] = [];
  numberOfGraphs = 2;

  ngOnInit() {

    let numberOfGraphs = localStorage.getItem("graphNumber");
    if (numberOfGraphs) {
      this.numberOfGraphs = +numberOfGraphs;
    }

    this.chartService.getAvailableCharts().subscribe(res => {
      this.options = res;
     });
  }

  ngOnDestroy(): void {
    localStorage.setItem("graphNumber", this.numberOfGraphs.toLocaleString());
  }

  changeGraphs(incrament: any) {
    this.numberOfGraphs += incrament;
  }
  

}
