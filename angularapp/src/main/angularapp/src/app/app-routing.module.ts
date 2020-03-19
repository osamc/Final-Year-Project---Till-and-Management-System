import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DefinitionComponent } from './definition/definition.component';
import { ProductListComponent } from './product-components/product-list/product-list.component';
import { ProductComponent } from './product-components/product/product.component';


const routes: Routes = [{path: "dashboard", component: DashboardComponent}, 
{path: "define", component: DefinitionComponent , children: [
  {path: "product/list", component: ProductListComponent},
  {path: "product/create", component: ProductComponent},
  {path: "product/edit/:id", component: ProductComponent}
]},
{path: "*", redirectTo: 'dashboard', pathMatch: 'full'},
{path: "", redirectTo: 'dashboard', pathMatch: 'full'}];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
