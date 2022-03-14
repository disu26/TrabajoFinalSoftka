import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../services/login.service'
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  usuario = {
    user:'',
    password:''
  }

  public status: boolean = false ;
  mensaje! : string ;


  constructor(
    private router : Router ,
    private _loginService : LoginService
  ) { }

  ngOnInit(): void {
  }

  signin(){

    this._loginService.singin(this.usuario)
      .subscribe(
        res =>  {
          localStorage.setItem('token',res.token);
          localStorage.setItem('idMongo',res.idMongo)
          localStorage.setItem('nombreUsuario',res.user)
          this.router.navigate(['/lobby']);
        },
        err =>console.log(<any>err)
      )
  }

}
