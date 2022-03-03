import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from '../models';
import { v4 as uuidv4 } from 'uuid';
import { ServerService } from '../services/server.service';
import { Router } from '@angular/router';
// import { sha1 } from '@angular/compiler/src/i18n/digest';
import * as sha1 from 'js-sha1';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user_summary_form!: FormGroup
  user_detail_form!: FormGroup
  user!: User
  toggleView: string = 'summary' // summary, detail, type
  type!: string
  
  buttonPressed: boolean = false

  constructor(private fb: FormBuilder, private svc: ServerService, private router: Router) { }

  ngOnInit(): void {
    this.user_summary_form=this.createForm();
    this.user_detail_form=this.createDetailForm();
    this.toggleView= 'summary'
    this.buttonPressed=false


    // TODO : run a service to get the list of existing email and username and save it as an array or make a service to check upon the button pressed
  }

  createForm() : FormGroup{
    return this.fb.group({
      'name': this.fb.control('',[Validators.required,Validators.minLength(3)]),
      'username': this.fb.control('',[Validators.required,Validators.minLength(3)]),
      'email': this.fb.control('',[Validators.required,Validators.email]),
      'password': this.fb.control('',[Validators.required,Validators.minLength(8)]),
      'checkbox': this.fb.control('',Validators.required)
    })
  }

  createDetailForm(): FormGroup{
    return this.fb.group({
      'location': this.fb.control('',[Validators.required,Validators.minLength(3)]),
      'bio': this.fb.control('',[Validators.required,Validators.minLength(3)]),
    })
  }

  valid() : boolean{
    return this.user_summary_form.valid 
  }

  checkthebox(): boolean{
    return this.user_summary_form.get('checkbox')!.value==true
  }

  createAccount(){
    this.buttonPressed=true

    if (!this.checkthebox()) {
      alert("Please agree to our terms and conditions by checking the checkbox below")
      return;
    }

    if (this.valid()) {
      // alert("Form is valid")
      this.user = this.user_summary_form.value as User;
     
      console.log(this.user);
      this.toggleDetail();
      
    }else{
      alert("Form is invalid")
      return;
    }
    
  }
  
  confirmProfile(){
    let userProfile  =this.user_detail_form.value as User
    this.user.bio=userProfile.bio
    this.user.location=userProfile.location
    // this.user.bio=this.user_detail_form.value('bio')
    // this.user.location=this.user_detail_form.value('location')
    console.log(this.user);
    this.toggleType()
  }
  
  finishRegistration(){
    this.user.type=this.type
    this.user.user_id=uuidv4();
    console.log(this.user);
    //send http post to SB
    this.svc.postRegistrationJSON(this.user).then(result=>{
      console.info("Yea")
      let authToken  = sha1(this.user.user_id)
      sessionStorage.setItem("authToken", authToken)
      this.navigateToList()
    }).catch(error=>{
      console.info(error.message)
      alert("Registration failed. Please try again")
      this.ngOnInit()
    })
    

  }

  toggleSummary(){
    this.toggleView='summary'
  }

  toggleDetail(){
    this.toggleView='detail'
  }

  toggleType(){
    this.toggleView='type'
  }

  navigateToList(){
    this.router.navigate(['list'])
  }
}

