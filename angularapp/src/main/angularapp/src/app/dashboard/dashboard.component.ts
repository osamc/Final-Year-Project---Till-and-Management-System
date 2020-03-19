import { Component, OnInit } from '@angular/core';

import {Chart, ChartOptions} from 'chart.js'

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor() { }

  test: Chart;
  test2: ChartOptions;

  charts: any[] = [];

  ngOnInit() {
    
      this.charts.push(new Chart('canvas0', {
        type: 'bar',
        data: {
            labels: ['Carling', 'Fosters', 'Worthingtons', 'Thatchers', 'Strongbow', 'Stella'],
            datasets: [{
                label: '# of Votes',
                data: [12, 19, 3, 5, 2, 3],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
              }}
      }));

      var data = data = {
        datasets: [{
            data: [10, 20, 30],
            backgroundColor: [
              'rgba(255, 99, 132, 0.2)',
              'rgba(255, 206, 86, 0.2)',
              'rgba(54, 162, 235, 0.2)',
          ],
        }],
    
        // These labels appear in the legend and in the tooltips when hovering different arcs
        labels: [
            'Largers',
            'Beers',
            'IPAS'
        ]
    };
    
      this.charts.push(new Chart('canvas1',  {
        type: 'doughnut',
        data: data,
        options: Chart.defaults.doughnut
    }));

    this.charts.push(new Chart('line-chart', {
      type: 'line',
      data: {
        labels: [1500,1600,1700,1750,1800,1850,1900,1950,1999,2050],
        datasets: [{ 
            data: [86,114,106,106,107,111,133,221,783,2478],
            label: "Wines",
            borderColor: "#3e95cd",
            fill: false
          }, { 
            data: [282,350,411,502,635,809,947,1402,3700,5267],
            label: "Spirits",
            borderColor: "#8e5ea2",
            fill: false
          }, { 
            data: [168,170,178,190,203,276,408,547,675,734],
            label: "Largers",
            borderColor: "#3cba9f",
            fill: false
          }, { 
            data: [40,20,10,16,24,38,74,167,508,784],
            label: "Liquers",
            borderColor: "#e8c3b9",
            fill: false
          }
        ]
      },
      options: {
        title: {
          display: true,
          text: 'Sales per category'
        }
      }
    }));

   
  }


  

}