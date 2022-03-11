import { Injectable } from '@angular/core';
import { HttpClient ,HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { Global } from "./global";

@Injectable({
  providedIn: 'root'
})
export class BingoService {

  public url!:String;

  constructor(
    private _http : HttpClient
  ) {
    this.url = Global.url
  }

  consultarTarjeta( id : any) : Observable<any>{

    let headers = new HttpHeaders().set('Content-Type','application/json');

    return this._http.get(this.url+"card/"+id,{headers:headers});
  }

}
