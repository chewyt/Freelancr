import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { HireComponent } from './components/hire.component';
import { ListComponent } from './components/list.component';
import { LoginComponent } from './components/login.component';
import { MainComponent } from './components/main.component';
import { MyprojectComponent } from './components/myproject.component';
import { NewFinterestComponent } from './components/new-finterest.component';
import { NewTransactionComponent } from './components/new-transaction.component';
import { NewComponent } from './components/new.component';
import { PrivateprojectComponent } from './components/privateproject.component';
import { ProjectComponent } from './components/project.component';
import { SigninComponent } from './components/signin.component';
import { ErrorComponent } from './error/error.component';

const routes: Routes = [
  {path:'',component:MainComponent},
  {path:'list',component:ListComponent},
  {path:'login',component:LoginComponent},
  {path:'signin',component:SigninComponent},
  {path:'hire',component:HireComponent},
  {path:'finterest/new/:project_id',component:NewFinterestComponent},
  // {path:'finterest/check/:finterest_id',component:NewFinterestComponent},
  {path:'project/:project_id',component:ProjectComponent},
  {path:'projects/freelancer/:user_id',component:MyprojectComponent},
  {path:'projects/client/:user_id',component:MyprojectComponent},
  {path:'projects/client/:user_id/:project_id',component:PrivateprojectComponent},
  {path:'projects/freelancer/:user_id/:project_id',component:PrivateprojectComponent},
  {path:'new',component:NewComponent},
  {path:'error',component:ErrorComponent},
  {path:'transaction/new/:project_id/:freelancer_id/:budget',component:NewTransactionComponent},
  // {path:'',component:ListComponent},
  // {path:'',component:ListComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes,{ useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
