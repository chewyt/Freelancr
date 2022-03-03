import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isLogin: boolean = false
  username: any

  constructor(private router:Router) { }

  ngOnInit(): void {
    if(sessionStorage.getItem("authToken")!=null){
      this.isLogin=true
      this.username = sessionStorage.getItem("0xab123001")
    }

  }


  signOut(){
    sessionStorage.clear()
    this.isLogin=false
    this.router.navigate([''])
  }
}
