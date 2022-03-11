import { Component, OnInit } from '@angular/core';
import { BingoService } from "../../services/bingo.service";
import { Router, ActivatedRoute, Params } from "@angular/router";

@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.css'],
  providers: [BingoService]
})
export class LobbyComponent implements OnInit {

  public tarjeta ! : any ;
  public headers = ['B','I','N','G','O'];

  constructor(
    private  _cardService : BingoService,
    private _route: ActivatedRoute
  ) { }

  ngOnInit(): void {

    this.llenarTarjeta();
  }

  llenarTarjeta(){

    let id = '14879898';

    this._cardService.consultarTarjeta(id).subscribe(
      response =>{
        this.tarjeta= response.data ;
        console.log(response.data)

      }, error => {
        console.log("Error : ")
          console.log(error)
      }
    )

  }

}
