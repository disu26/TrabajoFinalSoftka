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

  crearPartida( id : any) : Observable<any>{

    let headers = new HttpHeaders().set('Content-Type','application/json');

    return this._http.post(this.url+"game/"+id,{headers:headers});
  }

  agregarJugador( id : any) : Observable<any>{

    let headers = new HttpHeaders().set('Content-Type','application/json');

    return this._http.post(this.url+"game/addUser/"+id,{headers:headers});
  }

  partidaCreada() : Observable<any>{

    let headers = new HttpHeaders().set('Content-Type','application/json');

    return this._http.get(this.url+"game/started",{headers:headers});
  }

  partidaEnProgreso() : Observable<any>{

    let headers = new HttpHeaders().set('Content-Type','application/json');

    return this._http.get(this.url+"game/inProgress",{headers:headers});
  }

  listarUsuarios() : Observable<any>{

    let headers = new HttpHeaders().set('Content-Type','application/json');

    return this._http.get(this.url+"players",{headers:headers});
  }

  numeroBalota() : Observable<any>{

    let headers = new HttpHeaders().set('Content-Type','application/json');

    return this._http.put(this.url+"ballot/out",{headers:headers});

  }

  fechaPrimerUsuario(): Observable<any>{

    let headers = new HttpHeaders().set('Content-Type','application/json');

    return this._http.get(this.url+"game/startTime",{headers:headers});
  }

  esAdmin( id: any) : Observable<any>{

    let headers = new HttpHeaders().set('Content-Type','application/json');

    return this._http.get(this.url+"game/userAdmin/"+id,{headers:headers});
  }

  vectorBallotsOut(): Observable<any>{

    let headers = new HttpHeaders().set('Content-Type','application/json');

    return this._http.get(this.url+"game/ballotsOut",{headers:headers});
  }

  guardarCeldaBingo(id: any, balota:any): Observable<any>{

    let headers = new HttpHeaders().set('Content-Type','application/json');

    return this._http.put(this.url+"balcard/"+id+"/"+balota,{headers:headers});
  }

  esGanador(id:any): Observable<any>{

    let headers = new HttpHeaders().set('Content-Type','application/json');

    return this._http.get(this.url+"game/isWinner/"+id,{headers:headers});
  }

  finalizarJuego(): Observable<any>{

    let headers = new HttpHeaders().set('Content-Type','application/json');

    return this._http.put(this.url+"game/finish",{headers:headers});
  }

}
