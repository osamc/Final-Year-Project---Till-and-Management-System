/**
 * Till API
 * This app provides REST APIs for all associated till calls. From creating pages, products to transaction
 *
 * OpenAPI spec version: v1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import { Seller } from './seller';
import { TransactionRecord } from './transactionRecord';

export interface Transaction { 
    transactionId?: number;
    sellerId?: number;
    salesDate?: Date;
    transactions?: Array<TransactionRecord>;
    seller?: Seller;
}