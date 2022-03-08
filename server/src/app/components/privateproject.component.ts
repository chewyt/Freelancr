import { Component, Input, OnInit, Output } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, Subscription } from 'rxjs';
import { Contract, Finterest, Project, User } from '../models';
import { ServerService } from '../services/server.service';

import web3 from "../../../ethereum/web3.js";
import factory from "../../../ethereum/contract.js";
import compiledFreelancer from "../../../ethereum/contract_freelancer.js";
import Web3 from 'web3';

@Component({
  selector: 'app-privateproject',
  templateUrl: './privateproject.component.html',
  styleUrls: ['./privateproject.component.css']
})
export class PrivateprojectComponent implements OnInit {

  loading!: boolean

  project!:Project
  project_id!: string
  auth_token!: any
  auth_user!: User
  user_type = ''
  user_id_from_link!: string
  contract!: Contract


  temp_username: string=''
  temp_smart_contract_id: string=''
  temp_status: string ='initial'
  temp_project_cost:number=0

  finterests: Finterest[] =[]

  constructor(private ActivatedRoute: ActivatedRoute,
              private router: Router ,
              private svc: ServerService,
              public dialog: MatDialog) { }

  async ngOnInit(): Promise<void> {
    
    this.loading=false
    this.project_id=this.ActivatedRoute.snapshot.params['project_id']
    this.svc.dialogue_project_id.next(this.project_id)
    this.user_id_from_link=this.ActivatedRoute.snapshot.params['user_id']
    this.auth_token=sessionStorage.getItem("authToken")
    await this.svc.checkUserCreds(this.auth_token).then(result=>{
      this.auth_user=result
      this.user_type=this.auth_user.type
      // console.info("User type>>>>>>>>", this.user_type)
      if(this.user_id_from_link==this.auth_user.user_id){
        console.info("AUTHENTICATED :D")
      }else{
        sessionStorage.clear
        this.router.navigate([''])
      }
    })
    .catch(error=>{
      console.warn("Unable to retrieve user Type")
    })
    
    await this.getFinterest()
    await this.getProjectData()      
    // await this.getContractData()
    
  }

  getContractData(){
     //retrieve smart contract, freelancer, amount
     console.info("PRIVATE PROJECT COMPONENT: RETRIEVING CONTRACT DATA......")
     this.svc.getContractDetails(this.project_id,this.auth_user.user_id)
     .then(result=>{
       this.contract=result
       this.temp_username= this.contract.username
       this.temp_smart_contract_id =this.contract.smart_contract_id
       this.temp_status= this.contract.status
       this.temp_project_cost =this.contract.project_cost

       console.info('Retrieved contract details')
     }).catch(error=>{
       console.warn("Unable to retrieve contract details")

     })
  }


  getProjectData(){
    this.svc.getProject(this.project_id).then(result=>{
      this.project=result
      console.info("Project data retrieved")
      console.info("Display status:>>>>>>>>>>>>>",this.project.project_display)
      if(this.project.project_display){
        //Project is not deleted nor closed...
        console.info('Project status>>>>',this.project.project_status)
        if(!this.project.project_status){
        
          this.getContractData()
        }

      }else{
        console.warn("Project has been deleted")
        this.router.navigate(['/error'])
      }
    })
    .catch(error=>{
      console.warn("Unable to retrieve project")
    })
  }

  getFinterest(){
    this.svc.getFinterestbyProjectID(this.project_id).then(result=>{
      this.finterests=result
      console.info("Finterests retrieved successfully")
    })
    .catch(error=>{
      console.warn("Unable to retrieve finterest")
    })
  }
  
  openDeleteDialogue(){
    const dialogRef = this.dialog.open(DeleteDialogueContent);

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result==='deleted') {
        this.router.navigate(['/projects/'+this.auth_user.type+"/"+this.auth_user.user_id])
      }
    });
  }

  createTransaction(index:number){
    this.router.navigate(['/transaction/new/'+this.project_id+'/'+this.finterests[index].finterest_freelancer_id+'/'+this.finterests[index].finterest_budget])
  }

  progressBar(status:string):number{
    switch (status) {
      case 'initial':
        return 0;
      case 'funded':
        return 9;
      case 'started':
        return 27;
      case 'completed':
        return 47;
      case 'approved':
        return 67;
      case 'released':
        return 100;

    }
    return 0;
  }

  updateStatus = async(status:string)=>{

    switch (status) {
      case 'started':
          try {
            
            const accounts  = await web3.eth.getAccounts();
            console.log("Metamask account >>>>>> ",accounts[0])

            const freelancer = await new web3.eth.Contract(compiledFreelancer,this.temp_smart_contract_id);
            this.loading=true
            await freelancer.methods.startTask('0').send({
              from:accounts[0],
              gas:'2000000'
            });

            await this.updateContractStatusinSQL(status)

          } catch (error) {
            alert("[METAMASK]Transaction failed")
            this.loading=false
          }
        break;
      
      case 'approved':
          try {
            
            const accounts  = await web3.eth.getAccounts();
            console.log("Metamask account >>>>>> ",accounts[0])

            const freelancer = await new web3.eth.Contract(compiledFreelancer,this.temp_smart_contract_id);
            this.loading=true
            await freelancer.methods.approveTask('0').send({
              from:accounts[0],
              gas:'2000000'
            });
            
            await this.updateContractStatusinSQL(status)

          } catch (error) {
            alert("[METAMASK]Transaction failed")
            this.loading=false
          }
        break;

      case 'released':
          try {
            
            const accounts  = await web3.eth.getAccounts();
            console.log("Metamask account >>>>>> ",accounts[0])

            const freelancer = await new web3.eth.Contract(compiledFreelancer,this.temp_smart_contract_id);
            this.loading=true
            await freelancer.methods.releaseFunds('0').send({
              from:accounts[0],
              gas:'2000000',
            });

            await this.updateContractStatusinSQL(status)

          } catch (error) {
            alert("[METAMASK]Transaction failed")
            this.loading=false
          }
        break;
      
      case 'completed':
          await this.updateContractStatusinSQL(status)
        break;
    
      default:
        break;
    }
    
  }

  updateContractStatusinSQL(status:string){
    this.svc.updateContractStatus(status,this.project_id).then(result=>{
      console.info("Contract status updated to >>>>> ", status)
      this.ngOnInit()
    }).catch(error=>{
      console.warn(error.message)
    })
  }

}

@Component({
  selector: 'delete-dialogue-content',
  templateUrl: './delete-dialogue-content.html',
})
export class DeleteDialogueContent {

  project_id!: string
  

  constructor(private router:Router, 
    private svc: ServerService,
    public dialogRef: MatDialogRef<DeleteDialogueContent>,
    private ActivatedRoute: ActivatedRoute
    ){
    this.project_id=this.svc.project_id

      
    console.info("Dialogue: retrieved project id>>>>>>>",this.project_id)
  }

  close(){
    this.dialogRef.close();
  }
  deleteProject(){
    console.log("Deleting project......")
    this.svc.deleteProjectbyID(this.project_id).then(result=>{
      console.log("Project deleted....returning to your project list")
      
    })
    .catch(error=>{

    })
    this.dialogRef.close("deleted");
  }
}