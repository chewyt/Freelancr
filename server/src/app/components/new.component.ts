import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Project, User } from '../models';
import { ServerService } from '../services/server.service';

import * as sha1 from 'js-sha1';
import { v4 as uuidv4 } from 'uuid';
import * as moment from 'moment';




@Component({
  selector: 'app-new',
  templateUrl: './new.component.html',
  styleUrls: ['./new.component.css']
})
export class NewComponent implements OnInit {

  auth_token!: any
  auth_user!: User
  user_type=''
  
  isOverlay:boolean =true;
  loading:boolean =false;
  projectForm!: FormGroup
  project!: Project

  budget!: string
  budget_min:number = 100
  budget_max:number = 999999

  animation_selection!: boolean
  branding_selection!: boolean
  illustration_selection!: boolean
  web_selection!: boolean
  mobile_selection!: boolean
  ux_selection!: boolean

  specialization: any[] = [] 
  
  constructor(private fb: FormBuilder, private svc: ServerService, private router: Router) { }

  ngOnInit(): void {

    this.projectForm=this.createForm();
    this.loading=false
    this.isOverlay=true

    this.checkUserCreds()
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
      'project_title': this.fb.control('',[Validators.required,Validators.minLength(3)]),
      'project_brief': this.fb.control('',[Validators.required, Validators.minLength(80)]),
      'project_cost': this.fb.control('',[Validators.required,Validators.min(100)])
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
  

  backtoList(){
    this.router.navigate(['/list'])
  }

  submitProject(){
    this.validateBudget(this.budget)
    const budget_input = this.projectForm.get('project_cost')!.value

    if(!(budget_input>=this.budget_min&&budget_input<=this.budget_max &&this.projectForm.get('project_cost')!.valid)){
      alert("Please give a budget value based on selected range")
      return
    }
    if(!this.projectForm.valid){
      alert("Form is invalid")
      return
    }
    
    // this.specialization.push('test')

    //Consolidating the checkboxes
    this.specialization.splice(0)
    if(this.animation_selection)
      this.specialization.push("Animation")
    if(this.branding_selection)
      this.specialization.push("Branding")
    if(this.illustration_selection)
      this.specialization.push("Illustration")
    if(this.web_selection)
      this.specialization.push("Web Design")
    if(this.mobile_selection)
      this.specialization.push("Mobile Design")
    if(this.ux_selection)
      this.specialization.push("UX Design")

    if(this.specialization.length>3){
      alert("Please select up to three specializations.")
      
      return
    }
    if(this.specialization.length==0){
      alert("Please select at least one skill category.")
      return
    }

    //Checks on budget
    if (this.budget == null){
      alert("Please select a budget range.")
      return
    }

    // console.log(this.specialization)

    
    //Building Project object
    let project = this.projectForm.value as Project
    project.project_id = sha1(uuidv4())
    // console.info(project.project_id)
    project.project_specialties = this.specialization
    project.project_budget_range = this.budget

    project.project_status = true
    // let date = new Date()
    project.project_posted_date = moment().format("YYYY-MM-DD HH:mm:ss")
    project.project_display = true

    project.project_client_id = this.auth_user.user_id
    project.project_cost=budget_input
    
    // console.info(project)
    
    // //foreign key
    // project_client_id: string
    // project_freelancer_id: string
    // project_smart_contract_id: string

    // //Message owner button press to finalize price
    // project_cost: number //type decimal
    // // TODO: what variables required to allow owner to hire which freelancer for the job 

    // //after project completion
    // project_end_date: Date
    // project_rating: number  // 1 to 5 stars
    // project_reviews: string

    //service to post json data
    this.svc.postProjectJSON(project).then(result=>{
      console.info("Yea")
       setTimeout(async()=>{
         await this.router.navigate(['project',project.project_id])
        },1000)
      
      
      // let authToken  = sha1(this.user.user_id)
      // sessionStorage.setItem("authToken", authToken)
      // this.navigateToList()
    }).catch(error=>{
      console.info(error.message)
      alert("Posting of project failed. Please try again")
      this.ngOnInit()
    })

    this.loading=true


  }

}
