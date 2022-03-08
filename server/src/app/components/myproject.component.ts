import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Project, User } from '../models';
import { ServerService } from '../services/server.service';

@Component({
  selector: 'app-myproject',
  templateUrl: './myproject.component.html',
  styleUrls: ['./myproject.component.css']
})
export class MyprojectComponent implements OnInit {

  projects!: Project[]

  auth_token!: any
  auth_user!: User
  user_type= ''

  user_id_from_link!: string
  
  constructor(private ActivatedRoute: ActivatedRoute,private svc: ServerService, private router: Router) { }

  ngOnInit(): void {
    this.user_id_from_link=this.ActivatedRoute.snapshot.params['user_id']
    this.checkUserCreds()
  }

  checkUserCreds(){
    if(sessionStorage.getItem("authToken")!=null){
      this.auth_token=sessionStorage.getItem("authToken")
      
      this.svc.checkUserCreds(this.auth_token).then(result=>{
        this.auth_user=result
        this.user_type=this.auth_user.type
        console.info("MyProject: User type>>>>>>>>", this.user_type)
        if(this.user_id_from_link==this.auth_user.user_id){
          //SAME MEANS OK, AUTHENTICATED
          this.getMyProjects()
        }
        else{
          sessionStorage.clear
          this.router.navigate([''])
        }

      })
      .catch(error=>{
        console.warn("MyProject: Unable to retrieve user authenticity")
        sessionStorage.clear()
      })
    }
  }

  getMyProjects(){
    console.warn("ARE YOU KIDDING ME??")
    this.svc.getMyProjects(this.auth_user.user_id).then(result=>{
      this.projects=result
    })
    .catch(error=>{
      console.warn("Unable to retrieve  my projects")
    })
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

  viewMyProject(project_id:string){

      this.router.navigate(['/projects/'+this.auth_user.type+'/'+this.auth_user.user_id+'/'+project_id])

  }

}
