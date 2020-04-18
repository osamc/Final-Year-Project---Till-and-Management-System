import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { Configuration } from './configuration';
import { HttpClient } from '@angular/common/http';


import { ChartAPIService } from './api/chartAPI.service';
import { PageAPIService } from './api/pageAPI.service';
import { ProductAPIService } from './api/productAPI.service';
import { SellerAPIService } from './api/sellerAPI.service';
import { TransactionAPIService } from './api/transactionAPI.service';
import { JWTUserAPIService } from './api/JWTUserAPI.service';

@NgModule({
  imports:      [],
  declarations: [],
  exports:      [],
  providers: [
    ChartAPIService,
    JWTUserAPIService,
    PageAPIService,
    ProductAPIService,
    SellerAPIService,
    TransactionAPIService ]
})
export class ApiModule {
    public static forRoot(configurationFactory: () => Configuration): ModuleWithProviders {
        return {
            ngModule: ApiModule,
            providers: [ { provide: Configuration, useFactory: configurationFactory } ]
        };
    }

    constructor( @Optional() @SkipSelf() parentModule: ApiModule,
                 @Optional() http: HttpClient) {
        if (parentModule) {
            throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
        }
        if (!http) {
            throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
            'See also https://github.com/angular/angular/issues/20575');
        }
    }
}
