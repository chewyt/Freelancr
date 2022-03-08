import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ServerService } from '../services/server.service';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.css']
})
export class LoadingComponent implements OnInit,OnDestroy {

  sub$!: Subscription
  loading_status!: string
  constructor(private svc: ServerService) { }
  
  ngOnInit(): void {
    this.sub$ = this.svc.loading_status.subscribe(result=>{
      this.loading_status=result
    })
  }
  
  ngOnDestroy(): void {
    this.sub$.unsubscribe();
  }

}
