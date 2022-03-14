import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private URL:string;

  constructor(private http: HttpClient,
              private router : Router) {
    this.URL= 'http://localhost:3700/api'
  }

  singin(user:any){
    return this.http.post<any>(this.URL+"/ingresar",user)
  }

  loggedIn(){
    if(localStorage.getItem('token')){
      return true;
    }
    return false
  }

  getToken(){
    const token = localStorage.getItem('token');
    return token;
  }

}
