import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MAT_MOMENT_DATE_FORMATS,MomentDateAdapter,MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';
import { DateAdapter,MAT_DATE_FORMATS,MAT_DATE_LOCALE } from '@angular/material/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './material/material.module';
import { HttpClientModule } from "@angular/common/http";
import { FlexLayoutModule } from '@angular/flex-layout';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './components/login.component';
import { SigninComponent } from './components/signin.component';
import { ListComponent } from './components/list.component';
import { HeaderComponent } from './components/header.component';
import { MainComponent } from './components/main.component';
import { ServerService } from './services/server.service';
import { HireComponent } from './components/hire.component';
import { LoadingComponent } from './loading/loading.component';
import { ProjectComponent } from './components/project.component';
import { NewComponent } from './components/new.component';
import { NewFinterestComponent } from './components/new-finterest.component';
import { MyprojectComponent } from './components/myproject.component';
import { PrivateprojectComponent } from './components/privateproject.component';
import { ErrorComponent } from './error/error.component';
import { NewTransactionComponent } from './components/new-transaction.component';
import { BlockloadingComponent } from './loading/blockloading.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SigninComponent,
    ListComponent,
    HeaderComponent,
    MainComponent,
    HireComponent,
    LoadingComponent,
    ProjectComponent,
    NewComponent,
    NewFinterestComponent,
    MyprojectComponent,
    PrivateprojectComponent,
    ErrorComponent,
    NewTransactionComponent,
    BlockloadingComponent
  ],
  imports: [
    
    MaterialModule,
    HttpClientModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule
  ],
  providers: [ServerService,
    {provide:MAT_DATE_LOCALE,useValue:'en-SG'},
    {
      provide:DateAdapter,
      useClass:MomentDateAdapter,
      deps:[MAT_DATE_LOCALE,MAT_MOMENT_DATE_ADAPTER_OPTIONS]
    },
    {provide:MAT_DATE_FORMATS,useValue:MAT_MOMENT_DATE_FORMATS},
    { provide: Window, useValue: window }],
  bootstrap: [AppComponent]
})
export class AppModule { }
