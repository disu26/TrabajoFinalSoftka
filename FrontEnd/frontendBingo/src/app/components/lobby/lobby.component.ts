import { Component, OnInit } from '@angular/core';
import { BingoService } from "../../services/bingo.service";
import { Router, ActivatedRoute, Params } from "@angular/router";
import Swal from 'sweetalert2';


@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.css'],
  providers: [BingoService]
})
export class LobbyComponent implements OnInit {

  public tarjeta ! : any ;
  public headers = ['B','I','N','G','O'];
  public headers2 = ['ID','NOMBRE USUARIO'];
  public idUsuario! : any ;
  public listaUsuarios! : any;
  public nombreUsuario : any = '';
  public partidaCreadaSwitch : any = false;
  public minutes: number = 0;
  public seconds: number = 0;
  private timer: any;
  private date= new Date();

  public disabled: boolean = false;
  public animate: boolean = false;

  constructor(
    private  _cardService : BingoService,
    private _route: ActivatedRoute,
    private router : Router
  ) { }

  ngOnInit(): void {

    this.partidaCreada();
    this.listarUsuarios();

    this.nombreUsuario = localStorage.getItem('user');
    this.idUsuario = localStorage.getItem('idMongo');

  }

  updateTimer() {
    this.date.setMinutes(this.minutes);
    this.date.setSeconds(this.seconds);
    this.date.setMilliseconds(0);
    const time = this.date.getTime();
    this.date.setTime(time - 1000);  //---


    this.minutes = this.date.getMinutes();
    this.seconds = this.date.getSeconds();

    if( this.minutes ===0 && this.seconds === 0){
      Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Creando Partida',
        showConfirmButton: false,
        timer: 2500
      })
      this.router.navigate(['/game']);
    }

    if (this.date.getHours() === 0 &&
      this.date.getMinutes() === 0 &&
      this.date.getSeconds() === 0) {

      //stop interval
      clearInterval(this.timer);
      this.animate = true;
      setTimeout(() => {
        this.stop();

      }, 5000);

    }
  }

  increment(type:  'M' | 'S') {

     if (type === 'M') {
      if (this.minutes >= 59) return;
      this.minutes += 1;
    }
    else {
      if (this.seconds >= 59) return;
      this.seconds += 1;
    }
  }
  decrement(type:  'M' | 'S') {

     if (type === 'M') {
      if (this.minutes <= 0) return;
      this.minutes -= 1;
    }
    else {
      if (this.seconds <= 0) return;
      this.seconds -= 1;
    }
  }

  start() {
    if (this.minutes > 0 || this.seconds > 0) {

      this.disabled = true;
      this.updateTimer();

      if(this.seconds > 0){
        this.timer = setInterval(() => {
          this.updateTimer();
        }, 1000);
      }
    }
  }

  stop() {
    this.disabled = false;
    this.animate = false;
    clearInterval(this.timer);

  }

  reset() {
    this.minutes = 0;
    this.seconds = 0;
    this.stop();
  }

  manejadorTemporizador(tipo:any , cantidad: any, metodo:any){
    switch (metodo){
      case 1 :
        for(let i=0 ; i < cantidad ; i++){
          this.increment(tipo)
        }
        this.start();
        break;
      case 2 :
        for(let i=0 ; i < cantidad ; i++){
          this.decrement(tipo)
        }
        this.start();
        break;
      default:
        break;
    }

  }


  listarUsuarios(){
    this._cardService.listarUsuarios().subscribe(
      response => {
        console.log(this.listaUsuarios)
        this.listaUsuarios = response.data;

      },error => {
        console.log(error)
      }

    );



  }

  partidaCreada(){

    let id = this.idUsuario


    this._cardService.partidaCreada().subscribe(
      response => {
        if(response.data == false){

          this.crearPartida();
          this.llenarTarjeta();
          this.partidaCreadaSwitch = true;
          this.manejadorTemporizador('M',5, 1);

        }else if(response.data == true){
            this._cardService.partidaEnProgreso().subscribe(
              response => {
                if(response.data){
                  Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Hay una partida en progreso!',
                    footer: 'Intenta en un rato'
                  })

                }else if(response.data == false){

                  this._cardService.fechaPrimerUsuario().subscribe(

                    response => {
                      let fechaUsuario = new Date(response.data).getTime();
                      let tiempoActual = new Date().getTime()
                      let distance = tiempoActual - fechaUsuario;
                      let mins = Math.floor((distance % (1000*60*60))/(1000*60))
                      let seg = Math.floor((distance % (1000*60))/(1000))

                      this.manejadorTemporizador('M',5-mins,1);
                      this.manejadorTemporizador('S',seg,2 );



                    },error => {
                      console.log(error);

                    }
                  )
                  this._cardService.agregarJugador(id).subscribe(
                    response => {
                      this.llenarTarjeta();

                    },error => {
                      console.log(error.error.message);
                      this.llenarTarjeta();
                    }

                  );


                }
              },error => {
                console.log(error.error.message)
              }
            );
        }

      },error => {

        console.log(error)

      }
    );
  }




  crearPartida(){

    let id = this.idUsuario;

    this._cardService.crearPartida(id).subscribe(
      response => {
        console.log(response)
        this.llenarTarjeta();

      },error => {
        console.log(error.error.message)

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
