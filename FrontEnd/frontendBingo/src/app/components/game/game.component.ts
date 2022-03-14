import { Component, OnInit } from '@angular/core';
import { BingoService } from "../../services/bingo.service";
import { Router, ActivatedRoute, Params } from "@angular/router";
import Swal from 'sweetalert2';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  public tablaBingo! : any;
  public listaUsuarios! : any;
  public tarjeta ! : any ;
  public headers = ['B','I','N','G','O'];
  public headers2 = ['ID','NOMBRE USUARIO'];
  public idUsuario! : any ;
  public numeroBalota! : any;
  public partidaEnProgreso : any =true;
  public admin  : boolean = false;
  public vectorBallots! : any;
  public nombreUsuario : any = '';

  constructor(
    private  _cardService : BingoService,
    private _route: ActivatedRoute,
    private router : Router
  ) { }

  ngOnInit(): void {
    this.nombreUsuario = localStorage.getItem('user');
    this.idUsuario = localStorage.getItem('idMongo');
    this.listarUsuarios();
    this.llenarTarjeta();
    this.esAdmin();
  }

  guardarCelda(event :any) {

    this._cardService.guardarCeldaBingo(this.idUsuario, event.target.value).subscribe(

    )

  }

  verificar() {
    this._cardService.esGanador(this.idUsuario).subscribe(
      response => {
        if(response.data == true){
          Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Ha Ganado !!',
            showConfirmButton: false,
            timer: 2500
          })
          this._cardService.finalizarJuego().subscribe(
            response => {
              console.log(response)
            }
          )
        }else {
          Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'No has ganado, eliminado!!',
            showConfirmButton: false,
            timer: 2500
          })
        }
        this.router.navigate(['lobby'])
        console.log(response)


      }
    )

  }

  esAdmin(){


    this._cardService.esAdmin(this.idUsuario).subscribe(
      response => {

        if(response.data){
          this.admin = true;
        }else{
          this.admin = false
        }
      }
    )
  }

  generarNumAleatorio() {

    this._cardService.vectorBallotsOut().subscribe(
      response => {

        this.vectorBallots = response.data


      }
    )

  }

  generarNumeroAleatorioAdmin(){
    this.generarNumAleatorio();
    this._cardService.numeroBalota().subscribe(
      response => {
        this.numeroBalota = response.data
      }
    );
    return this.numeroBalota;
  }

  listarUsuarios(){
    this._cardService.listarUsuarios().subscribe(
      response => {
        this.listaUsuarios = response.data;

      },error => {
        console.log(error)
      }

    );

  }
  llenarTarjeta(){

    let id = this.idUsuario;

    this._cardService.consultarTarjeta(id).subscribe(
      response =>{
        this.tarjeta= response.data ;

      }, error => {
        console.log("Error : ")
        console.log(error)
      }
    )

  }

}
