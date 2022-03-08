import { Component, OnInit } from '@angular/core';
import web3 from "../../ethereum/web3.js";
import contract from "../../ethereum/contract.js";
import Web3 from 'web3';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'freelancr.io-client';
  
  constructor(){}

  async ngOnInit(): Promise<void> {
    const address  = contract.options.address;
    console.info('Contract address >>> ', address);
    // this.manager = await contract.methods.manager().call();
    // console.info('Manager of this contract: ',this.manager);
    const balance  = await web3.eth.getBalance(address);
    console.info('Balance of contract: ',balance,'wei');
  }
}
