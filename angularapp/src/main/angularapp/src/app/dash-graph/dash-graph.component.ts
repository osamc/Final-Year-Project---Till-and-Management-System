import { Component, OnInit, Input, AfterViewInit, ViewChild, ElementRef, OnDestroy, HostListener } from '@angular/core';
import { ChartAPIService } from '../openapi/api/chartAPI.service';
import * as Chart from 'chart.js';
import { WebsocketService } from '../services/websocket.service';

@Component({
  selector: 'app-dash-graph',
  templateUrl: './dash-graph.component.html',
  styleUrls: ['./dash-graph.component.css']
})
export class DashGraphComponent implements OnDestroy {

  //We use view child to get the canvas context used to show
  //the graph to the user
  @ViewChild('mychart', {static: true}) canvasRef: ElementRef;

  //A local variable for storing the chart, we only do this
  //so we are able to destroy the chart if we redraw a new chart
  chart: Chart = null;

  //The selected option from the select html input
  selectedGraph: string = "";

  //Options to be shown to the user
  options: string[] = [];

  //The options variable is set via a method, this is done so that 
  //we are able to ressurect the previously selected graph once we get
  //the option list
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

  //A value given to the component used for storing and retrieving
  //data from local storage
  @Input()
  id: string = "";

  /**
   * 
   * @param chartService this is used to retrieve the selected graph
   * to be diplayed within the application
   */
  constructor(private chartService: ChartAPIService,
      private websocketService: WebsocketService) {
        this.websocketService.ws.subscribe(res => {
          this.updateGraph();
        })
  }
  
  //We use both a hostlistener and the ondestroy angular event
  //to ensure that data is always saved
  @HostListener('window:unload', [ '$event' ])
  ngOnDestroy(): void {
    this.saveChoice();
  }


  //Method used to get the graph from the Server and draw it for the 
  //user
  updateGraph(): void {
    this.chartService.getChart(this.selectedGraph).subscribe(chart => {
      let cast: Chart.ChartConfiguration = JSON.parse(JSON.stringify(chart));
      if (this.chart) {
        this.chart.destroy();
      }
      this.chart = new Chart(this.canvasRef.nativeElement, cast);
    })
  }

  //If there is a selected choice, we want to save it so it can
  //be ressurected next time the page is loaded
  saveChoice(): void {
    if (this.selectedGraph && this.selectedGraph.length > 0) {
      localStorage.setItem("canvas" + this.id, this.selectedGraph);
    }
  }

   //Retrieves the previously selected choice
   getSaved():void {
    let graph = localStorage.getItem("canvas" + this.id);
    if (graph) {
      this.selectedGraph = graph;
    } else {
      this.selectedGraph = this.options[0];
    }
  }

}
