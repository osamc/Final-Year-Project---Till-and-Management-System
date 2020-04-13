import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'oapiDate'
})
export class OapiDatePipe implements PipeTransform {

  transform(value: any): string {

    if (value) {
      let time = this.padding(value[3]) + ":" +  this.padding(value[4]);
      let date = this.padding(value[2]) + "/" + this.padding(value[1]) + "/" + value[0];
  
  
      return time + " " + date;
    } else {
      return "";
    }
  
  }

  padding(n: number) {
    return (n < 10) ? ("0" + n) : n;
  }

}
