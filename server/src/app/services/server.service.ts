import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom, Subject } from 'rxjs';
import { Contract, Currency, Finterest, Project, Project_Count, User } from '../models';

@Injectable({
  providedIn: 'root'
})
export class ServerService {

  dialogue_project_id = new Subject<string>();
  project_id!:string

  loading_status = new Subject<string>();
  
  constructor(private http: HttpClient) { 

    this.dialogue_project_id.subscribe(value=>{
      this.project_id=value
    })

  }

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

  postProjectJSON(project:Project): Promise<String>{
    
    const url = '/api/project/new'
    
    const headers = new HttpHeaders()
    .set('content-type','application/json')
    // .set('Access-Control-Allow-Origin','*');


    return lastValueFrom(
      this.http.post<String>(url,project,{headers})
    )

  }

  getProjects():Promise<Project[]>{
    return lastValueFrom(
      this.http.get<Project[]>('/api/projects')
    )
  }
  
  getMyProjects(user_id:string):Promise<Project[]>{
    return lastValueFrom(
      this.http.get<Project[]>('/api/projects/'+user_id)
    )
  }
  
  getProjectCount():Promise<Project_Count>{
    return lastValueFrom(
      this.http.get<Project_Count>('/api/projects/count')
      )
    }
    
  getProject(id:string):Promise<Project>{
    console.warn("Retrieving project......")
    return lastValueFrom(
      this.http.get<Project>('/api/project/'+id)
    )
  }

  checkUserCreds(auth_token:string):Promise<User>{
    return lastValueFrom(
      this.http.get<User>('/api/type/'+auth_token)
    )
  }

  postFinterestJSON(finterest:Finterest): Promise<String>{

    const url = '/api/finterest/new'
    
    const headers = new HttpHeaders()
    .set('content-type','application/json')
    // .set('Access-Control-Allow-Origin','*');


    return lastValueFrom(
      this.http.post<String>(url,finterest,{headers})
    )
  }

  getFinterestbyProjectID(project_id:string):Promise<Finterest[]>{
    return lastValueFrom(
      this.http.get<Finterest[]>('/api/finterest/'+project_id)
    )
  }

  deleteProjectbyID(project_id:string):Promise<String>{
    
    return lastValueFrom(
      this.http.get<String>('/api/project/delete/'+project_id)
    )
  }

  convertETH(cost: number):Promise<Currency>{

    const url = '/api/currency'
    
    const record = new HttpParams()
    .set("project_cost",cost)


    const headers = new HttpHeaders()
    .set('content-type','application/x-www-form-urlencoded')


    return lastValueFrom(
      this.http.post<Currency>(url,record.toString(),{headers})
    )
  }
  createSmartContract(cl_id:string,fl_id:string,sc_id:string,p_id:string,cost:number):Promise<String>{

    const url = '/api/project/contract-test'  // '/api/project/contract'
    
    const record = new HttpParams()
    .set("client_id",cl_id)
    .set("freelancer_id",fl_id)
    .set("smart_contract_id",sc_id)
    .set("project_id",p_id)
    .set("project_cost",cost)
    


    const headers = new HttpHeaders()
    .set('content-type','application/x-www-form-urlencoded')


    return lastValueFrom(
      this.http.post<String>(url,record.toString(),{headers})
    )
  }

  getContractDetails(project_id:string,user_id:string):Promise<Contract>{
    return lastValueFrom(
      this.http.get<Contract>('/api/project/contract/'+project_id+'/'+user_id)
    )
  }

  updateContractStatus(contract_status:string,project_id:string){
    return lastValueFrom(
      this.http.get('/api/project/contract-update/'+project_id+'/'+contract_status)
    )
  }

}
