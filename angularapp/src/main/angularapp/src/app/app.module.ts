import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LocationStrategy, HashLocationStrategy, FormStyle } from '@angular/common';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DefinitionComponent } from './definition/definition.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LayoutModule } from '@angular/cdk/layout';
import { RouterModule } from '@angular/router';
import { ApiModule, BASE_PATH, Configuration } from './openapi';
import { HttpClientModule } from '@angular/common/http';
import { ProductListComponent } from './product-components/product-list/product-list.component';
import { ProductComponent } from './product-components/product/product.component';
import { PagelistComponent } from './page-components/pagelist/pagelist.component';
import { PageComponent } from './page-components/page/page.component';
import { SellerlistComponent } from './seller-components/sellerlist/sellerlist.component';
import { SellerComponent } from './seller-components/seller/seller.component';
import { ToasterComponent } from './toaster/toaster.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbPopoverModule, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { GroupsComponent } from './product-components/groups/groups.component';
import { TillbuttonComponent } from './tillbutton/tillbutton.component';
import { ReceiptService } from './receipt.service';
import { TillComponent } from './till/till.component';
import { ReceiptViewComponent } from './receipt-view/receipt-view.component';
import { environment } from 'src/environments/environment';
import { QuantityComponent } from './quantity/quantity.component';
import { TransactionViewComponent } from './transaction-view/transaction-view.component';
import { OapiDatePipe } from './oapi-date.pipe';
import { TotalPricePipe } from './total-price.pipe';


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    DefinitionComponent,
    ProductListComponent,
    ProductComponent,
    PagelistComponent,
    PageComponent,
    SellerlistComponent,
    SellerComponent,
    ToasterComponent,
    GroupsComponent,
    TillbuttonComponent,
    TillComponent,
    ReceiptViewComponent,
    QuantityComponent,
    TransactionViewComponent,
    OapiDatePipe,
    TotalPricePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    LayoutModule,
    RouterModule,
    ApiModule.forRoot(() => {
      return new Configuration({
        basePath: environment.basePath
      });
    }),
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgbPopoverModule,
    NgbModalModule
  ],
  providers: [ReceiptService, {
    provide: BASE_PATH, useValue: environment.basePath
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
