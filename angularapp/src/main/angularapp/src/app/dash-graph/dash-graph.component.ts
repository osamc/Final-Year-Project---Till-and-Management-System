import { Component, OnInit, Input, AfterViewInit, ViewChild, ElementRef, OnDestroy, HostListener } from '@angular/core';
import { ChartAPIService } from '../openapi/api/chartAPI.service';
import * as Chart from 'chart.js';

@Component({
  selector: 'app-dash-graph',
  templateUrl: './dash-graph.component.html',
  styleUrls: ['./dash-graph.component.css']
})
export class DashGraphComponent implements OnDestroy {

  @ViewChild('mychart', {static: true}) canvasRef: ElementRef;

  chart: Chart = null;
  selectedGraph: string = "";
  options: string[] = [];

  @Input()
  set setOptions(val: any) {
    if (this.options.length == 0) {
      this.options = val;
      this.selectedGraph = val[0];

      this.getSaved();

      if (this.selectedGraph) {
        this.updateGraph();
      }
    }
  }

  @Input()
  id: string = "";

  constructor(private chartService: ChartAPIService) { }
  
  @HostListener('window:unload', [ '$event' ])
  ngOnDestroy(): void {
    this.saveChoice();
  }

  saveChoice() {
    localStorage.setItem("canvas" + this.id, this.selectedGraph);
  }

  updateGraph() {
    this.chartService.getChart(this.selectedGraph).subscribe(chart => {
      let cast: Chart.ChartConfiguration = JSON.parse(JSON.stringify(chart));
      if (this.chart) {
        this.chart.destroy();
      }
      this.chart = new Chart(this.canvasRef.nativeElement, cast);
    })
  }

  getSaved() {
    let graph = localStorage.getItem("canvas" + this.id);
    if (graph) {
      console.log("got from storage: " +  graph);
      this.selectedGraph = graph;
    }
  }

}
