import { Injectable, Optional, Inject } from '@angular/core';
import { BASE_PATH } from '../openapi';
import { WebSocketSubject, webSocket } from 'rxjs/webSocket';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  public ws: WebSocketSubject<MessageEvent>;

  constructor( @Optional() @Inject(BASE_PATH) private basePath: string) { 
    
    if (this.basePath === "") {
      this.basePath = location.origin;
    }

    let wsPre = this.basePath.includes('https') ? "ws://" : "ws://";
    
    this.ws = webSocket({
      url: wsPre + this.basePath.replace('http://', '').replace('https://', '') +'/ws',
      deserializer: msg => msg,
    });
  }



}
