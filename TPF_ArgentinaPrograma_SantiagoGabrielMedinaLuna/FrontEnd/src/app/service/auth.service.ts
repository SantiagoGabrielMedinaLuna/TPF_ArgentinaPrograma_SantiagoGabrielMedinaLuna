  import { Injectable } from '@angular/core';
  import { HttpClient } from '@angular/common/http';
  import { NuevoUsuario } from '../model/nuevo-usuario';
  import { LoginUsuario } from '../model/login-usuario';
  import { Observable } from 'rxjs';
  import { JwtDto } from '../model/jwt-dto';
import { environment } from 'src/environments/environment';
  
  @Injectable({
    providedIn: 'root'
  })
  export class AuthService {
    //local
    //authURL = 'http://localhost:8080/auth/'; //este es el URL que estableciomos en el backend 
    URL = environment.URL + 'auth/';
  
    constructor(private httpClient: HttpClient) { }
  
    public nuevo(nuevoUsuario: NuevoUsuario): Observable<any>{
      return this.httpClient.post<any>(this.URL + 'nuevo', nuevoUsuario);
    }
   
    public login(loginUsuario: LoginUsuario): Observable<JwtDto>{
      return this.httpClient.post<JwtDto>(this.URL + 'login', loginUsuario)
    }
   }