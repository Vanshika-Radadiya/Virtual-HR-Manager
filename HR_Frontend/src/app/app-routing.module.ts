import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LanguageComponent } from './language/language.component';
import { LangComponent } from './lang/lang.component';
import { ReportComponent } from './report/report.component';
const routes: Routes = [
  {path:'language',component:LanguageComponent},
  {path:'lang',component:LangComponent},
  {path:'report',component:ReportComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
