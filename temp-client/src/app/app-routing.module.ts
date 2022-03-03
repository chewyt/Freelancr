import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { HireComponent } from './components/hire.component';
import { ListComponent } from './components/list.component';
import { LoginComponent } from './components/login.component';
import { MainComponent } from './components/main.component';
import { SigninComponent } from './components/signin.component';

const routes: Routes = [
  {path:'',component:MainComponent},
  {path:'list',component:ListComponent},
  {path:'login',component:LoginComponent},
  {path:'signin',component:SigninComponent},
  {path:'hire',component:HireComponent},
  // {path:'',component:ListComponent},
  // {path:'',component:ListComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
