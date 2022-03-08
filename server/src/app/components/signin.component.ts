import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {  Router } from '@angular/router';
import { User } from '../models';
import { ServerService } from '../services/server.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  form!: FormGroup
  buttonPressed:boolean = false
  authFailed:boolean = false
  

  constructor(private fb: FormBuilder, private svc: ServerService, private router:Router) { }

  ngOnInit(): void {
    this.form=this.createForm();
  }

  createForm(): FormGroup{
    return this.fb.group({
      'email': this.fb.control('',[Validators.required, Validators.email]),
      'password': this.fb.control('',[Validators.required, Validators.minLength(8)])

    })
  }

  signin(){
    
    
    this.buttonPressed=true
    
    if(this.form.invalid){
      return 
    }
    
    let userCred  = this.form.value as User
    // console.info("usercredentials>>>>>>>>>>>>>>",userCred)
    
    this.svc.authUser(userCred)
      .then(result=>{
        console.info("Success")
        // console.info(result)
        sessionStorage.setItem("authToken",result.auth_token) 
        
        this.router.navigate(['/list'])
         

      })
      .catch(error=>{
        console.info(error.message)
        this.authFailed=true
      })
  

  }
}
