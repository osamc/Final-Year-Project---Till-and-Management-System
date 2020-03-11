export * from './pageAPI.service';
import { PageAPIService } from './pageAPI.service';
export * from './productAPI.service';
import { ProductAPIService } from './productAPI.service';
export * from './sellerAPI.service';
import { SellerAPIService } from './sellerAPI.service';
export * from './transactionAPI.service';
import { TransactionAPIService } from './transactionAPI.service';
export const APIS = [PageAPIService, ProductAPIService, SellerAPIService, TransactionAPIService];
