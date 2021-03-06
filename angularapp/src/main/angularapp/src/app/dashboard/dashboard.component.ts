import { Component, OnInit, OnDestroy, HostListener, Optional, Inject } from '@angular/core';

import { ChartAPIService } from '../openapi/api/chartAPI.service';
import { SidebarService } from '../services/sidebar.service';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  /**
   * @param chartService used to get the list of options to be displayed
   */
  constructor(private chartService: ChartAPIService,
    private sidebar: SidebarService) {
      this.sidebar.setShowBurgerIcon(true);
      this.sidebar.setShowSideBar(true);
     }

  //The list of options to give to the child components
  options: any[] = [];
  //the number of graphs to be displayed on the dashboard
  numberOfGraphs = 2;

  ngOnInit() {
    this.getNumberOfCharts();
    this.chartService.getAvailableCharts().subscribe(res => {
      this.options = res;
    });
  }

  //Retrieve the number of charts from local storage
  getNumberOfCharts(): void {
    let numberOfGraphs = localStorage.getItem("graphNumber");
    if (numberOfGraphs) {
      this.numberOfGraphs = +numberOfGraphs;
    }
  }

  //Makes sure to save the number of charts within
  //local storage
  @HostListener('window:unload', ['$event'])
  ngOnDestroy(): void {
    localStorage.setItem("graphNumber", this.numberOfGraphs.toLocaleString());
  }

  //Changes the number of graphs to diplay within the browser
  changeGraphs(incrament: any): void {
    this.numberOfGraphs += incrament;
  }


}
