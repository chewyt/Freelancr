import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Currency, Project, User } from '../models';
import { ServerService } from '../services/server.service';

import web3 from "../../../ethereum/web3.js";
import factory from "../../../ethereum/contract.js";
import compiledFreelancer from "../../../ethereum/contract_freelancer.js";
import Web3 from 'web3';

@Component({
  selector: 'app-new-transaction',
  templateUrl: './new-transaction.component.html',
  styleUrls: ['./new-transaction.component.css']
})
export class NewTransactionComponent implements OnInit {

  project_id!: string
  freelancer_id!: string
  smart_contract_id!:string


  auth_token!: any
  auth_user!: User
  user_type=''
  
  isOverlay:boolean =true;
  loading:boolean =false;
  project!: Project

  
  project_cost!: number 
  project_cost_eth!: Currency 

  constructor(private ActivatedRoute: ActivatedRoute, private svc: ServerService, private router: Router) { }

  async ngOnInit():Promise<void> {
    
    const accounts  = await web3.eth.getAccounts();
    console.log(accounts[0])

    this.project_id=this.ActivatedRoute.snapshot.params['project_id']
    this.freelancer_id=this.ActivatedRoute.snapshot.params['freelancer_id']
    this.project_cost=parseFloat(this.ActivatedRoute.snapshot.params['budget'])

    this.checkUserCreds()
    this.extractProjectData()

    this.loading=false
    this.isOverlay=true

    this.svc.convertETH(this.project_cost).then(result=>{
      this.project_cost_eth =result
      console.log("Done>>>",this.project_cost_eth.amount)
    })
    .catch(error=>{
      console.warn("Ended with error")
    })


  }

  extractProjectData(){
    this.svc.getProject(this.project_id).then(result=>{
      this.project=result
      console.info("Project data retrieved")
      console.info("budget to be converted>>>>>>",this.project_cost)

    })
    .catch(error=>{
      console.warn("Unable to retrieve project")
    })
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

  backtoProject(){
    this.router.navigate(['/projects/client/'+this.auth_user.user_id+'/'+this.project_id])
  }

  createContract = async()=>{
       
    this.loading=true
    console.info('Converted wei:>>>>>',web3.utils.toWei(this.project_cost_eth.amount.toString(),'ether'))//

    const accounts  = await web3.eth.getAccounts();
    console.log("Metamask account >>>>>> ",accounts[0])
    
    try{
      this.svc.loading_status.next('Creating smart contracts')
      await factory.methods.createContract('100000000').send({
        from:accounts[0],
        gas:'2000000'
      });
      const addresses = await factory.methods.getDeployedContracts().call();
      this.smart_contract_id=addresses[addresses.length-1];
      // console.log('Smart contract deployed at',addresses[addresses.length-1])
      
      //Add schedule
      
      const freelancer = await new web3.eth.Contract(compiledFreelancer,this.smart_contract_id);
      this.svc.loading_status.next('Adding project schedule for smart payment')
      await freelancer.methods.addSchedule('DP','Design',web3.utils.toWei(this.project_cost_eth.amount.toString(),'ether')).send({
        from:accounts[0],
        gas:'2000000'
      });
      
      this.svc.loading_status.next('Confirming schedule')
      await freelancer.methods.acceptProject().send({
        from:accounts[0],
        gas:'2000000'
      });
      
      this.svc.loading_status.next('Funding project')
      await freelancer.methods.fundTask('0').send({
        from:accounts[0],
        gas:'2000000',
        value: web3.utils.toWei(this.project_cost_eth.amount.toString(),'ether')
      });
      
      this.svc.loading_status.next('Blockchain recorded into centralized DB')
      alert("[METAMASK] SUCCESS")
      //should send to freelancer for checks and start Task
      // this.loading=false
      //updating the SQL on following points
      
      this.svc.createSmartContract(this.auth_user.user_id,this.freelancer_id,this.smart_contract_id,this.project_id,this.project_cost)
      .then(result=>{
        console.info("Succeeded in updating SQL")
        this.backtoProject()
      }).catch (gg=>{
        alert("SQL Transaction failed. Contact us for assistance.")
        this.loading=false
      }) 
      
    } catch (error) {
      alert("[METAMASK] Transaction failed")
      this.loading=false
      
    }
    
    
    
    
    


  }

  


}
