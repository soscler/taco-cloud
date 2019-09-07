import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {RecentTacosComponent} from './recent-tacos/recent-tacos.component';


const routes: Routes = [
  { path: 'recent', component: RecentTacosComponent}
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
