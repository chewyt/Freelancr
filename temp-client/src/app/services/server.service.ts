import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { User } from '../models';

@Injectable({
  providedIn: 'root'
})
export class ServerService {

  constructor(private http: HttpClient) { }

  postRegistrationJSON(user:User): Promise<String>{
    
    const url = '/api/register'
    
    const headers = new HttpHeaders()
    .set('content-type','application/json')
    // .set('Access-Control-Allow-Origin','*');


    return lastValueFrom(
      this.http.post<String>(url,user,{headers})
    )

  }

  authUser(user:User): Promise<User>{
    
    const url = '/api/login'
    
    const headers = new HttpHeaders()
    .set('content-type','application/json')
    // .set('Access-Control-Allow-Origin','*');


    return lastValueFrom(
      this.http.post<User>(url,user,{headers})
    )

  }
}
