import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DefinitionComponent } from './definition/definition.component';
import { ProductListComponent } from './product-components/product-list/product-list.component';
import { ProductComponent } from './product-components/product/product.component';
import { GroupsComponent } from './product-components/groups/groups.component';
import { SellerlistComponent } from './seller-components/sellerlist/sellerlist.component';
import { SellerComponent } from './seller-components/seller/seller.component';
import { PagelistComponent } from './page-components/pagelist/pagelist.component';
import { PageComponent } from './page-components/page/page.component';
import { TillComponent } from './till/till.component';
import { TransactionViewComponent } from './transaction-view/transaction-view.component';


const routes: Routes = [{path: "dashboard", component: DashboardComponent}, 
{path: "define", component: DefinitionComponent , children: [
  {path: "product/list", component: ProductListComponent},
  {path: "product/create", component: ProductComponent},
  {path: "product/edit/:id", component: ProductComponent},
  {path: "group/create", component: GroupsComponent},
  {path: "group/edit/:id", component: GroupsComponent},
  {path: "seller/list", component: SellerlistComponent},
  {path: "seller/create", component: SellerComponent},
  {path: "seller/edit/:id", component: SellerComponent},
  {path: "page/list", component: PagelistComponent},
  {path: "page/create", component: PageComponent},
  {path: "page/edit/:id", component: PageComponent},
  {path: "*", redirectTo: "product/list", pathMatch: 'full'}, 
  {path: "", redirectTo: "product/list", pathMatch: 'full'},

]},
{path: "till", component: TillComponent},
{path: "transview", component: TransactionViewComponent},
{path: "*", redirectTo: 'dashboard', pathMatch: 'full'},
{path: "", redirectTo: 'dashboard', pathMatch: 'full'}];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
