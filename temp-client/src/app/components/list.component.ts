import * as sha1 from 'js-sha1';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  isOverlay:boolean =false;
  budget!: string
  projectForm!: FormGroup

  constructor(private fb: FormBuilder) {   }

  ngOnInit(): void {
    this.projectForm=this.createForm();
  }

  createForm(): FormGroup{
    return this.fb.group({
      
    })
  }

  createProject(){
    this.isOverlay=true
  }

  closeOverlay(){
    this.isOverlay=false
  }

  submitProject(){

  }
}
