import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models';
import { ServerService } from '../services/server.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isLogin: boolean = false
  auth_token!: any
  auth_user!: User
  user_type=''

  constructor(private router:Router, private svc: ServerService) { }

  ngOnInit(): void {

    
    if(sessionStorage.getItem("authToken")!=null){
      this.auth_token=sessionStorage.getItem("authToken")
      
      this.svc.checkUserCreds(this.auth_token).then(result=>{
        this.auth_user=result
        this.user_type=this.auth_user.type
        console.info("Header: User type>>>>>>>>", this.user_type)
        this.isLogin=true
      })
      .catch(error=>{
        console.warn("Header: Unable to retrieve user authenticity")
        sessionStorage.clear()
      })
    }

  }


  signOut(){
    sessionStorage.clear()
    this.isLogin=false
    this.router.navigate([''])
  }
}
