import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatChipsModule} from '@angular/material/chips';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatListModule} from '@angular/material/list';
import {MatRadioModule} from '@angular/material/radio';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatDividerModule} from '@angular/material/divider';
import {MatIconModule} from '@angular/material/icon';
import { MatOptionModule } from '@angular/material/core';
import {MatSelectModule} from '@angular/material/select';
import { MatDialogModule } from '@angular/material/dialog';
import {MatProgressBarModule} from '@angular/material/progress-bar';



const MATERIAL = [
    MatButtonModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatDatepickerModule,
    MatInputModule,
    MatFormFieldModule,
    MatListModule,
    MatRadioModule,
    MatToolbarModule, 
    MatDividerModule,
    MatIconModule,
    MatOptionModule,
    MatSelectModule,
    MatDialogModule,
    MatProgressBarModule
    
];

@NgModule({
  declarations: [],
  imports: MATERIAL,
  exports: MATERIAL
})
export class MaterialModule { }