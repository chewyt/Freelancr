import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Project, User, Finterest } from '../models';
import { ServerService } from '../services/server.service';

import * as sha1 from 'js-sha1';
import { v4 as uuidv4 } from 'uuid';
import * as moment from 'moment';

@Component({
  selector: 'app-new-finterest',
  templateUrl: './new-finterest.component.html',
  styleUrls: ['./new-finterest.component.css']
})
export class NewFinterestComponent implements OnInit {

  project_id!: string


  auth_token!: any
  auth_user!: User
  user_type=''
  
  isOverlay:boolean =true;
  loading:boolean =false;
  finterestForm!: FormGroup
  project!: Project

  budget!: string
  budget_min:number = 100
  budget_max:number = 999999
  project_cost: number = 100

  hasBargain:boolean =false

  
  
  constructor(private ActivatedRoute: ActivatedRoute,private fb: FormBuilder, private svc: ServerService, private router: Router) { }

  ngOnInit(): void {
    
    this.project_id=this.ActivatedRoute.snapshot.params['project_id']

    this.hasBargain=false
    this.checkUserCreds()
    this.extractProjectData()

    this.finterestForm=this.createForm();
    this.loading=false
    this.isOverlay=true


  }

  extractProjectData(){
    this.svc.getProject(this.project_id).then(result=>{
      this.project=result
      console.info("Project data retrieved")
      this.budget=this.project.project_budget_range
      console.info(this.budget)
      this.project_cost=this.project.project_cost
      console.info("budget>>>>>>",this.project.project_cost)
      this.validateBudget(this.budget)

    })
    .catch(error=>{
      console.warn("Unable to retrieve project")
    })
  }

  bargain(){
    this.hasBargain=true
  }
  checkUserCreds(){
    if(sessionStorage.getItem("authToken")!=null){
      this.auth_token=sessionStorage.getItem("authToken")
      
      this.svc.checkUserCreds(this.auth_token).then(result=>{
        this.auth_user=result
        this.user_type=this.auth_user.type
        console.info("Header: User type>>>>>>>>", this.user_type)
      })
      .catch(error=>{
        console.warn("Header: Unable to retrieve user authenticity")
        sessionStorage.clear()
      })
    }
  }

  createForm(): FormGroup{
    return this.fb.group({
      'finterest_budget': this.fb.control(''),
      'finterest_comments': this.fb.control('',[Validators.required, Validators.minLength(10)]),
    })
  }

  validateBudget(budget: string) {
    switch (budget) {
      case 'b_100to1K':
        this.budget_min=100
        this.budget_max=1000
        break;
      case 'b_1Kto10K':
        this.budget_min=1000
        this.budget_max=10000
        break;
        case 'b_10Kto20K':
        this.budget_min=10000
        this.budget_max=20000
        break;
        case 'b_20Kto50K':
        this.budget_min=20000
        this.budget_max=50000
        break;
        case 'b_over50K':
        this.budget_min=50000
        break;
    }
    return ''

  }
  

  backtoProject(){
    this.router.navigate(['/project/'+this.project_id])
  }

  submitInterest(){
    
    if(!this.finterestForm.valid){
      alert("Please fill up message to the owner")
      return
    }
    
   
    let finterest = this.finterestForm.value as Finterest
    finterest.finterest_id = sha1(uuidv4())
    finterest.finterest_freelancer_id = this.auth_user.user_id
    finterest.finterest_status = false
    
    if(finterest.finterest_budget==0){
      finterest.finterest_budget=this.project_cost
    }

    finterest.finterest_project_id = this.project_id

    //service to post json data
    this.svc.postFinterestJSON(finterest).then(result=>{
      console.info("Yea")
       setTimeout(async()=>{
         await this.router.navigate(['finterest',finterest.finterest_id])
        },1000)
      
      alert("Interest sent to owner!")
      this.router.navigate(['/project/'+this.project_id])
    }).catch(error=>{
      console.info(error.message)
      alert("You have indicated your interest. Returning back...")
      this.router.navigate(['/project/'+this.project_id])
    })

    this.loading=true


  }

}
