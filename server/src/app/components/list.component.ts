import { Component, Inject, OnInit } from '@angular/core';
import { Project, Project_Count, User } from '../models';
import { ServerService } from '../services/server.service';
import { Router } from '@angular/router';
import { JsonpClientBackend } from '@angular/common/http';
import { FormBuilder, FormGroup } from '@angular/forms';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'PleaseSignIn',
  templateUrl: './PleaseSignIn.html',
})
export class PleaseSignInDialogue {

  constructor(private router:Router, public dialogRef: MatDialogRef<PleaseSignInDialogue>,
    ){}
  signIn(){
    this.dialogRef.close();
  }
}


@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  
  currentTime!: Date
  projects!: Project[]
  project_count!: number 
  
  auth_token!: any
  auth_user!: User
  user_type= ''

  animation_selection!: boolean
  branding_selection!: boolean
  illustration_selection!: boolean
  web_selection!: boolean
  mobile_selection!: boolean
  ux_selection!: boolean
  
  b_100to1K_selection!: boolean
  b_1Kto10K_selection!: boolean
  b_10Kto20K_selection!: boolean
  b_20Kto50K_selection!: boolean
  b_over50K_selection!: boolean


  filterForm!: FormGroup

  constructor(private svc: ServerService, private router: Router, private fb: FormBuilder, private dialog: MatDialog) {   }

  ngOnInit(): void {
    this.filterForm=this.createForm();

    if(sessionStorage.getItem("authToken")!=null){
      //means can be correct auth token or some random string
      this.auth_token=sessionStorage.getItem("authToken")
      
      this.svc.checkUserCreds(this.auth_token).then(result=>{
        this.auth_user=result
        this.user_type=this.auth_user.type
        console.info("List: User type>>>>>>>>", this.user_type)
      })
      .catch(error=>{
        console.warn("List: Unable to retrieve user authenticity")
        sessionStorage.clear()
      })
    }

    this.currentTime=new Date()
    this.svc.getProjects().then(result=>{
      this.projects=result
    })
    .catch(error=>{
      console.warn("Unable to retrieve projects")
    })
    this.svc.getProjectCount().then(result=>{
      let today_count = result as Project_Count
      this.project_count=today_count.count
    })
    .catch(error=>{
      console.warn("Unable to retrieve project count")
    })
  }

  createForm(): FormGroup{
    return this.fb.group({
      'search': this.fb.control('')
    })
  }
  filterResults(){

  }
  
  createProject(){

    if(this.user_type===''){
      const dialogue = this.dialog.open(PleaseSignInDialogue)
      dialogue.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
        this.router.navigate(['/signin'])
      });
    }

    if(this.user_type==='client'){
      this.router.navigate(['/new'])
    }
  }

  viewProject(id:string){

    if(this.user_type===''){
      const dialogue = this.dialog.open(PleaseSignInDialogue)
      dialogue.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
        this.router.navigate(['/signin'])
      });
    }else{

      this.router.navigate(['/project/'+id])
    }

  }

  convertSkillsArrayfromJSON(array: string):string[]{
    return JSON.parse(array)
  }
  
  convertBudgetfromEnum(budget: string):string {
    switch (budget) {
      case 'b_100to1K':
        return '$100 - $1K'
      case 'b_1Kto10K':
        return '$1K - $10K'
      case 'b_10Kto20K':
        return '$10K - $20K'
      case 'b_20Kto50K':
        return '$20K - $50K'
      case 'b_over50K':
        return 'Over $50K'
    }
    return ''

  }

  
}
