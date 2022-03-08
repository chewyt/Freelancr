import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { stringify } from 'querystring';
import { Project, User } from '../models';
import { ServerService } from '../services/server.service';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {

  project_id!: string
  project!: Project
  auth_token!: any
  auth_user!: User
  user_type = ''
  hasAuthenticated: boolean = false


  user_id_from_link!:string

  constructor(private ActivatedRoute: ActivatedRoute,
              private router: Router ,
              private svc: ServerService) { }

  ngOnInit(): void {
    console.log("Pulling data from server....")
    this.project_id=this.ActivatedRoute.snapshot.params['project_id']
    this.auth_token=sessionStorage.getItem("authToken")
    console.log("Auth token>>>>>>>>",this.auth_token)
    this.svc.getProject(this.project_id).then(result=>{
      this.project=result
      console.info("Project data retrieved")
      console.info("Display status:>>>>>>>>>>>>>",this.project.project_display)
      if(this.project.project_display){
        //Project is not deleted nor closed...
      }else{
        console.warn("Project has been deleted")
        this.router.navigate(['/error'])
      }
    })
    .catch(error=>{
      console.warn("Unable to retrieve project")
    })
    this.svc.checkUserCreds(this.auth_token).then(result=>{
      this.auth_user=result
      this.user_type=this.auth_user.type
      console.info("User type>>>>>>>>", this.user_type)

      try {
        this.user_id_from_link=this.ActivatedRoute.snapshot.params['user_id']
      } catch (error) {
        //do nothing
        console.warn("didnt get snapshot of user_id")
        console.warn(this.user_id_from_link)
      }
      if(this.user_id_from_link==this.auth_user.user_id){
        console.info("Private Project Component: Authenticated")
        this.hasAuthenticated=true
      }

    })
    .catch(error=>{
      console.warn("Unable to retrieve user Type")
    })

    

  }
  displaySkills(array: string):string[]{
    
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

  backtoList(){
    
    
    if (this.user_id_from_link==null){
      
      console.warn("Reverting to List")
      this.router.navigate(['/list'])
    }
    else{
      console.warn("Reverting to my projects :D")
      this.router.navigate(['/projects/client/'+this.user_id_from_link])
    }
    

  }

}
