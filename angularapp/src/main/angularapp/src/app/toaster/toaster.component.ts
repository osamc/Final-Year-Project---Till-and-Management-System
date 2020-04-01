import { Component, OnInit } from '@angular/core';
import { ToasterService, ToastType } from '../toaster/toaster.service';


@Component({
  selector: 'app-toaster',
  templateUrl: './toaster.component.html',
  styleUrls: ['./toaster.component.css']
})
export class ToasterComponent {

  constructor(public toaster: ToasterService) { }

}